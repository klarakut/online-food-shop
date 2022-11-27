package myprojects.foodshop.service;

import myprojects.foodshop.models.Cart;
import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.cartDtos.AddToCartDto;
import myprojects.foodshop.models.dtos.cartDtos.CartDto;
import myprojects.foodshop.models.dtos.orderDtos.OrderResponseDto;

import java.util.Collection;

public interface CartService {
    OrderResponseDto addToCart(AddToCartDto dto, User user);
    OrderResponseDto addToCart(Long itemId, User user);

    Collection<Cart> getAll(User user);

    OrderResponseDto removeItem(Long itemId, User user);

    OrderResponseDto UpdateItemInCart(Long itemId, User user);

    CartDto listCartItems(User user);

    void deleteUserCartItems(User user);
}
