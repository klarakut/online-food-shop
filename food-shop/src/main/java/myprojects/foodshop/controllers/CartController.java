package myprojects.foodshop.controllers;

import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.ResponseDto;
import myprojects.foodshop.models.dtos.cartDtos.AddToCartDto;
import myprojects.foodshop.models.dtos.cartDtos.CartDto;
import myprojects.foodshop.models.dtos.orderDtos.OrderResponseDto;
import myprojects.foodshop.service.CartService;
import myprojects.foodshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/user/cart")
    public ResponseEntity<? extends ResponseDto> showCart(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        // Collection<Cart> items = cartService.getAll(user);
        CartDto items = cartService.listCartItems(user);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/user/carts")
    public ResponseEntity<? extends ResponseDto> addItemsToCart(@RequestBody AddToCartDto dto,
                                                               HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = cartService.addToCart(dto,user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/user/cart")
    public ResponseEntity<? extends ResponseDto> addItemToCart(@RequestParam("id") Long itemId,
                                                               HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = cartService.addToCart(itemId,user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/user/cart")
    public ResponseEntity<? extends ResponseDto> removeItem(@RequestParam("id") Long itemId, HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = cartService.removeItem(itemId, user);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PatchMapping("/user/cart")
    public ResponseEntity<? extends ResponseDto> updateItem(@RequestParam ("id") Long itemId,
                                                            HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = cartService.UpdateItemInCart(itemId,user);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
}
