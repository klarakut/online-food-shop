package myprojects.foodshop.service;

import myprojects.foodshop.exceptions.EmptyBucketException;
import myprojects.foodshop.exceptions.NotEnoughItemsException;
import myprojects.foodshop.exceptions.ProductNotFoundException;
import myprojects.foodshop.models.Cart;
import myprojects.foodshop.models.Product;
import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.cartDtos.AddToCartDto;
import myprojects.foodshop.models.dtos.cartDtos.CartDto;
import myprojects.foodshop.models.dtos.cartDtos.CartItemDto;
import myprojects.foodshop.models.dtos.orderDtos.OrderResponseDto;
import myprojects.foodshop.repositories.CartRepository;
import myprojects.foodshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CartServiceImpl implements CartService{

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public OrderResponseDto addToCart(AddToCartDto dto, User user) {
        Product product = productRepository.findById(dto.id).orElseThrow(ProductNotFoundException::new);

        Integer quantityInStock = product.getQuantity();
        if(quantityInStock < dto.quantity){
            throw new NotEnoughItemsException();
        }
        product.setQuantity(quantityInStock- dto.quantity);
        productRepository.save(product);

        Collection<Cart> items = cartRepository.findAllByUserId(user.getId());
        for (Cart c : items){
            if (c.getProduct().getId().equals(dto.id)){
                c.setQuantity(c.getQuantity() + dto.quantity);
                c.setCreatedDate(new Date());
                cartRepository.save(c);
                return new OrderResponseDto("Item added");
            }
        }
        Cart cart = new Cart(product,user, dto.quantity);
        cartRepository.save(cart);
        return new OrderResponseDto("Item added");
    }

    @Override
    public OrderResponseDto addToCart(Long itemId, User user) {
        Product product = productRepository.findById(itemId).orElseThrow(ProductNotFoundException::new);

        Integer quantityInStock = product.getQuantity();
        if(quantityInStock == 0){
            throw new NotEnoughItemsException();
        }
        product.setQuantity(quantityInStock - 1);
        productRepository.save(product);

        Collection<Cart> items = cartRepository.findAllByUserId(user.getId());
        for (Cart c : items){
            if (Objects.equals(c.getProduct().getId(), itemId)){
                c.setQuantity(c.getQuantity() + 1);
                c.setCreatedDate(new Date());
                cartRepository.save(c);
                return new OrderResponseDto("Item added");
            }
        }
        Cart cart = new Cart(product,user);
        cartRepository.save(cart);
        return new OrderResponseDto("Item added");
    }

    @Override
    public OrderResponseDto removeItem(Long itemId, User user) {
        Collection<Cart> items = cartRepository.findAllByUserId(user.getId());
        for (Cart c : items) {
            if (c.getId() == itemId) {
                Product product = c.getProduct();
                product.setQuantity(product.getQuantity() + c.getQuantity());
                productRepository.save(product);
                cartRepository.deleteAllByIdAndUserId(itemId,user.getId());
                return new OrderResponseDto("Item removed");
            }
        }
        throw new ProductNotFoundException();
    }

    @Override
    public OrderResponseDto UpdateItemInCart(Long itemId, User user) {
        Collection<Cart> items = cartRepository.findAllByUserId(user.getId());
        for (Cart c : items){
            if (c.getId() == itemId){
                if(c.getQuantity() >= 1){
                    c.setQuantity(c.getQuantity() - 1);
                    Product product = c.getProduct();
                    product.setQuantity(product.getQuantity() + 1);
                    productRepository.save(product);
                    if(c.getQuantity() == 0){
                        cartRepository.deleteAllByIdAndUserId(c.getId(),user.getId());
                    } else {
                        cartRepository.save(c);
                    }
                    return new OrderResponseDto("Item removed");
                } else {
                    throw new NotEnoughItemsException();
                }
            }
        }
        throw new ProductNotFoundException();
    }

    @Override
    public Collection<Cart> getAll(User user) {
        Collection<Cart> items = cartRepository.findAllByUserId(user.getId());
        if (items.isEmpty()){
            throw new EmptyBucketException();
        }
        return items;
    }

    @Override
    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }

    public static CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }

    /*public void deleteCartItems(int userId) {
        cartRepository.deleteAll();
    }*/

    @Override
    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}
