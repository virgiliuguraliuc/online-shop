package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.service.CartService;
import org.fasttrackit.onlineshop.steps.CustomerSteps;
import org.fasttrackit.onlineshop.transfer.cart.AddProductToCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceIntegrationTests {
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerSteps customerSteps;

    @Test
    public void testAddToCart_whenNewCart_thenCreateCart() {
        Customer customer = customerSteps.createCustomer();
        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setCustomerId(customer.getId());
        //todo: replace this after mapping Cart-Product relationship
        request.setProductId(10L);
        cartService.addProductToCart(request);
    }



}
