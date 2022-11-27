package myprojects.foodshop.models;

import myprojects.foodshop.models.dtos.BuyItem;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;
    //private LocalDateTime orderDateTime;

    private boolean payed = false;

    private Double finalPrice;

    @ManyToOne private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<BuyItem> items = new ArrayList<>();

    public Bucket() {
    }

    public Long getId() {
        return id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public User getUser() {
        return user;
    }

    public List<BuyItem> getItems() {
        return items;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed() {
        this.payed = true;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addToOrder(BuyItem item){
        items.add(item);
    }

    public void deleteItem(BuyItem item){
        items.remove(item);
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
