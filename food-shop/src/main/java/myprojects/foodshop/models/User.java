package myprojects.foodshop.models;

import myprojects.foodshop.models.dtos.userDtos.CreateUserDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private String name;
    private String surname;
    private String phoneNumber;
    private String city;

    @OneToMany(mappedBy = "user")
    private Collection<Bucket> buckets;

    @ManyToMany//(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String email, String password, String name, String surname, String phoneNumber, String city, Collection<Role> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.roles = roles;
    }

    public User(CreateUserDto createUserDto) {
        this.email = createUserDto.email;
        this.password = createUserDto.password;
        this.name = createUserDto.name;
        this.surname = createUserDto.surname;
        this.phoneNumber = createUserDto.phoneNumber;
        this.city = createUserDto.city;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public Collection<Bucket> getOrders() {
        return buckets;
    }

    public boolean addOrderToUser(Bucket bucket){
        return buckets.add(bucket);
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
