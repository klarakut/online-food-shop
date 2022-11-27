package myprojects.foodshop.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import myprojects.foodshop.exceptions.OrderNotFoundException;
import myprojects.foodshop.models.Order;
import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.ApiResponse;
import myprojects.foodshop.models.dtos.ResponseDto;
import myprojects.foodshop.models.dtos.orderDtos.CheckoutItemDto;
import myprojects.foodshop.models.dtos.orderDtos.StripeResponse;
import myprojects.foodshop.service.OrderService;
import myprojects.foodshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    // stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<? extends ResponseDto> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtos) throws StripeException {
        // create the stripe session
        Session session = orderService.createSession(checkoutItemDtos);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        // send the stripe session id in response
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }

    // place order after checkout
    @PostMapping("/add")
    public ResponseEntity<? extends ResponseDto> placeOrder(HttpServletRequest request, HttpServletResponse response) {
        // public ResponseEntity<? extends ResponseDto> placeOrder(@RequestParam("sessionId") String sessionId, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getUserFromToken(request);
        // place the order
        // orderService.placeOrder(user, sessionId);
        orderService.placeOrder(user);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(HttpServletRequest request, HttpServletResponse response){
        // validate token and retrieve user
        User user = userService.getUserFromToken(request);
        // get orders
        List<Order> orderDtoList = orderService.listOrders(user);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }


    // get orderitems for an order
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        // validate token and retrieve user
        User user = userService.getUserFromToken(request);
        try {
            Order order = orderService.getOrder(id,user);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    /* old:
    // place order after checkout
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // place the order
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // get orders
        List<Order> orderDtoList = orderService.listOrders(user);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }


    // get orderitems for an order
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token)
            throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    } */
}
