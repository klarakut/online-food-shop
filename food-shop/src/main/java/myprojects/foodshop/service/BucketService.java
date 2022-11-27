package myprojects.foodshop.service;


import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.BuyItem;
import myprojects.foodshop.models.dtos.orderDtos.OrderResponseDto;

import java.util.Collection;

public interface BucketService {
    OrderResponseDto addToOrder(String item, Integer count, User user);
    OrderResponseDto addToOrder(String item, User user);
    Collection<BuyItem> showItemsInOrder(User user);

    OrderResponseDto removeItemFromOrder(String item, User user);
    OrderResponseDto removeItemFromOrder(String item, Integer count, User user);

    OrderResponseDto cancelOrder(User user);

    OrderResponseDto payOrder(User user);
}
