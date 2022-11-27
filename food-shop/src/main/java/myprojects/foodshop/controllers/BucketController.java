package myprojects.foodshop.controllers;

import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.BuyItem;
import myprojects.foodshop.models.dtos.ResponseDto;
import myprojects.foodshop.models.dtos.orderDtos.OrderResponseDto;
import myprojects.foodshop.service.BucketService;
import myprojects.foodshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
@RequestMapping("/api")
public class BucketController {

    private final BucketService bucketService;
    private final UserService userService;

    public BucketController(BucketService bucketService, UserService userService) {
        this.bucketService = bucketService;
        this.userService = userService;
    }


    // -> creation of the order
    // -> cancellation of the order
    // -> payment of the order

   /* @PostMapping("/products")
    //public ResponseEntity<? extends ResponseDto> store(@RequestBody ProductRequestDto dto){
    public ResponseEntity<? extends ResponseDto> store(@RequestBody ProductCreateDto dto){
        ProductResponseDto product = productService.store(dto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }*/

    @PostMapping("/user/addItems")
    public ResponseEntity<? extends ResponseDto> addItemsToOrder(@RequestParam("item") String item, @RequestParam("count") Integer count,
                                                                 HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = bucketService.addToOrder(item,count,user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/user/addItem")
    public ResponseEntity<? extends ResponseDto> addItemToOrder(@RequestParam("item") String item, HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = bucketService.addToOrder(item,user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/user/items")
    public Collection<BuyItem> showMeOrder(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        return bucketService.showItemsInOrder(user);
    }

    @DeleteMapping("/user/items")
    public ResponseEntity<? extends ResponseDto> removeItem(@RequestParam("item") String item, HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = bucketService.removeItemFromOrder(item, user);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PostMapping("/user/items")
    public ResponseEntity<? extends ResponseDto> removeItem(@RequestParam("item") String item,@RequestParam("count") Integer count,
                                                            HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = bucketService.removeItemFromOrder(item,count,user);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @DeleteMapping("/user/orders")
    public ResponseEntity<? extends ResponseDto> cancelOrder(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = bucketService.cancelOrder(user);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PatchMapping("/user/orders")
    public ResponseEntity<? extends ResponseDto> payOrder(HttpServletRequest request, HttpServletResponse response){
        User user = userService.getUserFromToken(request);
        OrderResponseDto responseDto = bucketService.payOrder(user);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
}
