package myprojects.foodshop.repositories;

import myprojects.foodshop.models.Product;
import myprojects.foodshop.models.dtos.BuyItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyItemRepository extends JpaRepository<BuyItem,Long> {

    // Optional<BuyItem> findBuyItemByProduct(Product product);
    BuyItem findBuyItemByProduct(Product product);
}
