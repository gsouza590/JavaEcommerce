package com.gabriel.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "phone_number"}))
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    private String country;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private String image;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "customers_roles", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Roles> roles;

    public Customer(){
        this.shoppingCart= new ShoppingCart();
        this.orders= new ArrayList<>();
    }

}