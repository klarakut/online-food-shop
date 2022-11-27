package myprojects.foodshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import myprojects.foodshop.models.dtos.BuyItem;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "total_price")
    private Double totalPrice;

    /*@Column(name = "session_id")
    private String sessionId;*/

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Collection<BuyItem> orderItems;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Order() {
    }

    // getters
    public Date getCreatedDate() {
        return createdDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    // public String getSessionId() {return sessionId;}

    public Collection<BuyItem> getOrderItems() {
        return orderItems;
    }

    public User getUser() {
        return user;
    }

    // setters
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // public void setSessionId(String sessionId) {this.sessionId = sessionId;}

    public void setOrderItems(Collection<BuyItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
