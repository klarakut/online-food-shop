package myprojects.foodshop.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import myprojects.foodshop.exceptions.OrderNotFoundException;
import myprojects.foodshop.models.Order;
import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.orderDtos.CheckoutItemDto;

import java.util.List;

public interface OrderService {

    Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException;
    //void placeOrder(User user, String sessionId);
    void placeOrder(User user);
    List<Order> listOrders(User user);
    Order getOrder(Long orderId, User user) throws OrderNotFoundException;
}
