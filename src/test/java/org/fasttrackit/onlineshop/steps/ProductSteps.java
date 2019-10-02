package org.fasttrackit.onlineshop.steps;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ProductSteps {
    @Autowired
    private ProductService productService;


    public Product createProduct() {
        SaveProductRequest request = new SaveProductRequest();
        request.setName("Computer");
        request.setDescription("computes things");
        request.setPrice(2000);
        request.setQuantity(2);
        request.setImagePath("over there");


        Product product = productService.createProduct(request);

        assertThat(product, notNullValue());
        assertThat(product.getId(), notNullValue());
        assertThat(product.getId(), greaterThan((0L)));
        assertThat("unexpected product name",product.getName(), is(request.getName()));
        //we can specify the reason for the failure of the test like above
        assertThat(product.getDescription(), is(request.getDescription()));
        assertThat(product.getPrice(), is(request.getPrice()));
        assertThat(product.getImagePath(), is(request.getImagePath()));

        return product;}
}
