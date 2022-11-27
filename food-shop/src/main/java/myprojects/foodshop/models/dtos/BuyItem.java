package myprojects.foodshop.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import myprojects.foodshop.models.Order;
import myprojects.foodshop.models.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "orderitems")
public class BuyItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity = 1;

    @Column(name = "created_date")
    private Date createdDate;
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")    // or @Transient
    private Product product;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "price")
    private @NotNull double price;

    public BuyItem() {
    }

    public BuyItem(double price, int quantity, Date createdDate, Product product, Order order) {
        this.price = price;
        this.quantity = quantity;
        this.createdDate = new Date();
        this.product = product;
        this.order = order;
    }

    // getters
    public Double getPrice() {
        return getProduct().getPrice() * quantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Order getOrder() {
        return order;
    }

    // setters
    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int amount) {
        this.quantity = amount;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
