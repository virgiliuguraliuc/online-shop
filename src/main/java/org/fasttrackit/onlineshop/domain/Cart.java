package org.fasttrackit.onlineshop.domain;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Customer customer;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "cart_products", joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    public void addToCart(Product product){
        //adding received product to current cart
        products.add(product);
        //associating current cart with the received product
        product.getCarts().add(this);
    }

    private void removeFromCart(Product product) {
        //adding received product to current cart
        products.remove(product);
        //associating current cart with the received product
        product.getCarts().remove(this);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return id != null ? id.equals(cart.id) : cart.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
