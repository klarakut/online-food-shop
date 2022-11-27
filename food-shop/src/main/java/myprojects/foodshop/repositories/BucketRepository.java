package myprojects.foodshop.repositories;

import myprojects.foodshop.models.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BucketRepository extends JpaRepository<Bucket,Long> {

    Optional<Bucket> findOrderByPayedFalse();
    //Optional<Order> findOrderByPayedFalse();
}
