package myprojects.foodshop.repositories;

import myprojects.foodshop.models.Order;
import myprojects.foodshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
