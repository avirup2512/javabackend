package com.example.ecommerce.Product.Rating;

import java.sql.Timestamp;
import java.util.List;

import com.example.ecommerce.Product.Product;
import com.example.ecommerce.Users.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "rating")
@Table(name = "rating")
public class RatingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "value")
    double value;

    @Column(name = "time")
    String publishedOn;

    @ManyToMany
    @JoinTable(name = "Rating_Product", joinColumns = @JoinColumn(name = "rating_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    List<Product> product;

    @ManyToMany
    @JoinTable(name = "Rating_User", joinColumns = @JoinColumn(name = "rating_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> users;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getPublishedOn() {
        return this.publishedOn;
    }

    public void setUser(List<User> users) {
        this.users = users;
    }

    public List<User> getUser() {
        return this.users;
    }

    public void setProduct(List<Product> products) {
        this.product = products;
    }

    public List<Product> getProduct() {
        return this.product;
    }
}
