package myprojects.foodshop.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @JsonIgnore
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private int quantity = 1;

    public Cart() {
    }

    public Cart(Product product, User user, int quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
        this.createdDate = new Date();
    }

    public Cart(Product product, User user) {
        this.product = product;
        this.user = user;
        this.createdDate = new Date();
    }

    // getters
    public Long getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public int getQuantity() {
        return quantity;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
