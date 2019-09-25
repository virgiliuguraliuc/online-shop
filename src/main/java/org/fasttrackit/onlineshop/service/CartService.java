package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Cart;
import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.persistance.CartRepository;
import org.fasttrackit.onlineshop.transfer.cart.AddProductToCartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CustomerService.class);

    //IoC = inversion of Control
    private final CartRepository cartRepository;
    private final CustomerService customerService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
    }

    @Transactional
    public void addProductToCart(AddProductToCartRequest request){
        LOGGER.info("Adding product to cart");
        Cart cart = cartRepository.findById(request.getCustomerId())
                .orElse(new Cart());

        if(cart.getCustomer() ==null){
            LOGGER.debug("cart doesnt exist, retrieving customer to create a new cart");
            Customer customer = customerService.getCustomer(request.getCustomerId());
            cart.setCustomer(customer);
        }
        cartRepository.save(cart);

    }

}
