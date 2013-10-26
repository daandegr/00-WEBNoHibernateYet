package models;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    
    @OneToMany(mappedBy="user")
    private List<Product> products;

    public User() {
    }

    public User(long userId, String firstName, String lastName, String email, List<Product> products) {
        this.userId=userId;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.products = products;
    }

    /* Getters en setters voor de verschillende attributen van het Model */
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        if (userId < 1) {
            throw new IllegalArgumentException(
                    "User number may not be negative, value = " + userId);
        }
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
       
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    
}
