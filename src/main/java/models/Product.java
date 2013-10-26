/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Daan
 */
@Entity
public class Product {
    
    
    @Id
    @GeneratedValue
    private long productId;
    
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
    private String productName;
    private double price;
    
    public Product() {
    }
    
    public Product(long pId, User user, String pName, double price) {
        productId = pId;
        this.user = user;
        productName = pName;
        this.price = price;
    }

    
    
    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public long getUserId(){
        return user.getUserId();
    }
    
}
