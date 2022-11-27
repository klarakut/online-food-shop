package myprojects.foodshop.service;

import myprojects.foodshop.exceptions.EmptyBucketException;
import myprojects.foodshop.exceptions.NotEnoughItemsException;
import myprojects.foodshop.exceptions.OrderNotFoundException;
import myprojects.foodshop.exceptions.ProductNotFoundException;
import myprojects.foodshop.models.Bucket;
import myprojects.foodshop.models.Product;
import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.BuyItem;
import myprojects.foodshop.models.dtos.orderDtos.OrderResponseDto;
import myprojects.foodshop.repositories.BucketRepository;
import myprojects.foodshop.repositories.BuyItemRepository;
import myprojects.foodshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final BuyItemRepository buyItemRepository;

    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, BuyItemRepository buyItemRepository) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.buyItemRepository = buyItemRepository;
    }

    //TODO
    // 1. creation of the order
    // 2. cancellation of the order
    // 3. payment of the order


    //TODO
    // 1. creation of the order
    // find item and get a count of that item
    //      -> if count of item is bigger than required items than just add required count to the basket
    //          + sniz pocet na skladu o pocet pridany do kosiku
    //      -> if there is less than required count => throw new exception with missing items

    @Override
    public OrderResponseDto addToOrder(String nameOfItem, Integer count, User user) {
        //try {
        Optional<Product> existingProduct = productRepository.findByName(nameOfItem);
        if (existingProduct.isEmpty()){
            throw new ProductNotFoundException();
        }

        Product product = existingProduct.get();
        Integer quantityInStock = product.getQuantity();
            if(quantityInStock < count){
                throw new NotEnoughItemsException();
            }

        product.setQuantity(quantityInStock-count);
        productRepository.save(product);

        Collection<Bucket> buckets = user.getOrders();
        for (Bucket bucket : buckets){
            if (!bucket.isPayed()) {
                Collection<BuyItem> items = bucket.getItems();
                for(BuyItem buyItem : items){
                    if (buyItem.getProduct().getName().equals(nameOfItem)){
                        buyItem.setQuantity(buyItem.getQuantity() + count); //? or (buyItem.getAmount() + 1)
                        bucket.setFinalPrice(bucket.getFinalPrice() + product.getPrice()*count);
                        bucketRepository.save(bucket);
                        return new OrderResponseDto("Item added");
                    }
                }
                BuyItem item = new BuyItem();
                item.setProduct(product);
                item.setQuantity(count);
                bucket.addToOrder(item);
                bucket.setFinalPrice(item.getPrice());
                bucketRepository.save(bucket);
                return new OrderResponseDto("Item added");
            }
        }
        BuyItem item = new BuyItem();
        item.setProduct(product);
        item.setQuantity(count);
        Bucket bucket = new Bucket();
        bucket.addToOrder(item);
        bucket.setFinalPrice(item.getPrice());
        //orders.add(order);
        bucket.setUser(user);
        bucketRepository.save(bucket);
        //orders.add(order);
        user.addOrderToUser(bucket);

        /*
        Optional<Order> o = orderRepository.findOrderByPayedFalse();
        if (o.isEmpty()){
            Order order = new Order();
            BuyItem item = new BuyItem();
            item.setProduct(product);
            item.setAmount(count);

            order.addToOrder(item);
            order.setFinalPrice(item.getPrice());
            orderRepository.save(order);
        } else {
            Order order = o.get();
            Collection<BuyItem> items = order.getItems();
            BuyItem i = buyItemRepository.findBuyItemByProduct(product);
            for(BuyItem buyItem : items){
                if (buyItem.equals(i)){
                    i.setAmount(i.getAmount() + count);
                    order.setFinalPrice(order.getFinalPrice() + product.getPrice()*count);
                    orderRepository.save(order);
                    return new OrderResponseDto("Item added");
                }
            }
                BuyItem item = new BuyItem();
                item.setProduct(product);
                item.setAmount(count);

                order.addToOrder(item);
                order.setFinalPrice(order.getFinalPrice() + item.getPrice());
                orderRepository.save(order);
        }*/
        return new OrderResponseDto("Item added");
    }

    @Override
    public OrderResponseDto addToOrder(String nameOfItem, User user) {
        //try {
        Optional<Product> existingProduct = productRepository.findByName(nameOfItem);
        if (existingProduct.isEmpty()){
            throw new ProductNotFoundException();
        }

        Product product = existingProduct.get();
        Integer quantityInStock = product.getQuantity();
        if(quantityInStock == 0){
            throw new NotEnoughItemsException();
        }

        product.setQuantity(--quantityInStock);
        productRepository.save(product);

        Collection<Bucket> buckets = user.getOrders();
       /* if(orders.isEmpty()){
            Order order = new Order();
            BuyItem item = new BuyItem();
            item.setProduct(product);

            order.addToOrder(item);
            order.setFinalPrice(item.getPrice());
            orders.add(order);
            orderRepository.save(order); // ?
            return new OrderResponseDto("Item added");
        }*/

        for (Bucket bucket : buckets){
            if (!bucket.isPayed()) {
                Collection<BuyItem> items = bucket.getItems();
                for(BuyItem buyItem : items){
                    if (buyItem.getProduct().getName().equals(nameOfItem)){
                        buyItem.setQuantity(buyItem.getQuantity() + 1);
                        bucket.setFinalPrice(bucket.getFinalPrice() + product.getPrice());
                        bucketRepository.save(bucket);
                        return new OrderResponseDto("Item added");
                    }
                }
                BuyItem item = new BuyItem();
                item.setProduct(product);

                bucket.addToOrder(item);
                bucket.setFinalPrice(item.getPrice());
                bucketRepository.save(bucket); // ? je to potreba
                //orders.add(order);
                //user.addOrderToUser(order);
                return new OrderResponseDto("Item added");
            }
        }
        //Optional<Order> o = orderRepository.findOrderByPayedFalse();
        /*if (o.isEmpty()){
            Order order = new Order();
            BuyItem item = new BuyItem();
            item.setProduct(product);

            order.addToOrder(item);
            order.setFinalPrice(item.getPrice());
            orderRepository.save(order);
        } else {*/
           /* Order order = o.get();
            Collection<BuyItem> items = order.getItems();
            // Optional<BuyItem> i = buyItemRepository.findBuyItemByProduct(product);
            BuyItem i = buyItemRepository.findBuyItemByProduct(product);
            for(BuyItem buyItem : items){
                if (buyItem.equals(i)){
                    buyItem.setAmount(buyItem.getAmount() + i.getAmount()); //? or (buyItem.getAmount() + 1)
                    order.setFinalPrice(order.getFinalPrice() + product.getPrice());
                    orderRepository.save(order);
                    return new OrderResponseDto("Item added");
                }
            }*/
            BuyItem item = new BuyItem();
            item.setProduct(product);
            //item.setAmount(count);
            Bucket bucket = new Bucket();
            bucket.addToOrder(item);
            bucket.setFinalPrice(bucket.getFinalPrice() + item.getPrice());
            bucket.setUser(user);
            bucketRepository.save(bucket);
            // orders.add(order);
            user.addOrderToUser(bucket);
            //}
        //}
        return new OrderResponseDto("Item added");

        /*} catch (Exception e){
            throw new UnknownErrorException();
        }*/
    }
    @Override
    public Collection<BuyItem> showItemsInOrder(User user) {
        Collection<Bucket> buckets = user.getOrders();
        for(Bucket bucket : buckets){
            if(!bucket.isPayed()){
                Collection<BuyItem> items = bucket.getItems();
                return items;
            }
        }

        /*Optional<Order> order = orderRepository.findOrderByPayedFalse();
        if (order.isPresent()){
            Collection<BuyItem> items = order.get().getItems();
            return items;
        }*/
        throw new EmptyBucketException();
        //return new ResponseEntity<>()
    }

    @Override
    public OrderResponseDto removeItemFromOrder(String item, User user) {
        Collection<Bucket> buckets = user.getOrders();
        for (Bucket bucket : buckets){
            if (!bucket.isPayed()){
                Collection<BuyItem> items = bucket.getItems();
                for(BuyItem buyItem : items){
                    if (buyItem.getProduct().getName().equals(item)){
                        int count = buyItem.getQuantity();
                        Product product = buyItem.getProduct();
                        product.setQuantity(product.getQuantity() + count);
                        productRepository.save(product);
                        bucket.deleteItem(buyItem);
                        bucket.setFinalPrice(bucket.getFinalPrice() - buyItem.getPrice());
                        bucketRepository.save(bucket);
                        return new OrderResponseDto("Item removed");
                    }
                }
            }
        }
        throw new ProductNotFoundException();
        /*for(BuyItem buyItem : items){
            if (buyItem.getProduct().getName().equals(item)){
                int count = buyItem.getAmount();
                Product product = buyItem.getProduct();
                product.setQuantity(product.getQuantity() + count);
                productRepository.save(product);
                order.deleteItem(buyItem);
                order.setFinalPrice(order.getFinalPrice() - buyItem.getPrice());
                orderRepository.save(order);
            } else {
                throw new ProductNotFoundException();
            }
        }
        return new OrderResponseDto("Item removed");*/
    }

    @Override
    public OrderResponseDto removeItemFromOrder(String item, Integer count, User user) {
        Collection<Bucket> buckets = user.getOrders();
        for (Bucket bucket : buckets){
            if (!bucket.isPayed()){
                Collection<BuyItem> items = bucket.getItems();
                for(BuyItem buyItem : items){
                    if (buyItem.getProduct().getName().equals(item)){
                        buyItem.setQuantity(buyItem.getQuantity() - count);
                        Product product = buyItem.getProduct();
                        product.setQuantity(product.getQuantity() + count);
                        productRepository.save(product);
                        //order.deleteItem(buyItem);
                        bucket.setFinalPrice(bucket.getFinalPrice() - product.getPrice() * count);
                        bucketRepository.save(bucket);
                        return new OrderResponseDto("Item removed");
                    }
                }
            }
        }
        throw new ProductNotFoundException();

        /*for(BuyItem buyItem : items){
            if (buyItem.getProduct().getName().equals(item)){
                //int amount = buyItem.getAmount() - count;
                buyItem.setAmount(buyItem.getAmount() - count);
                Product product = buyItem.getProduct();
                product.setQuantity(product.getQuantity() + count);
                productRepository.save(product);
                //order.deleteItem(buyItem);
                order.setFinalPrice(order.getFinalPrice() - product.getPrice() * count);
                orderRepository.save(order);
            } else {
                throw new ProductNotFoundException();
            }
        }
        return new OrderResponseDto("Item removed");*/
    }


    //TODO -> 2. cancellation of the order
        // muzu smazat order podle id(?)
    @Override
    public OrderResponseDto cancelOrder(User user) {
        Collection<Bucket> buckets = user.getOrders();
        for (Bucket bucket : buckets){
            if (!bucket.isPayed()){
                Collection<BuyItem> items = bucket.getItems();
                for(BuyItem i : items){
                    int count = i.getQuantity();
                    Product product = i.getProduct();
                    product.setQuantity(product.getQuantity() + count);
                }
                bucketRepository.delete(bucket);
                return new OrderResponseDto("Order removed");
            }
        }
        throw new OrderNotFoundException();
    }

    //TODO -> 3. payment of the order
    @Override
    public OrderResponseDto payOrder(User user) {
        Collection<Bucket> buckets = user.getOrders();
        for (Bucket bucket : buckets){
            if(!bucket.isPayed()){
                bucket.setPayed();
                bucketRepository.save(bucket);
                return new OrderResponseDto("Order has been payed");
            }
        }
        throw new OrderNotFoundException();
    }
}
