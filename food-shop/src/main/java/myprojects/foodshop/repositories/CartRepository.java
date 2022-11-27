package myprojects.foodshop.repositories;

import myprojects.foodshop.models.Cart;
import myprojects.foodshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Collection<Cart> findAllByUserId(Long userId);
    void deleteAllByIdAndUserId (Long itemId, Long userId);
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
    void deleteByUser(User user);
}
