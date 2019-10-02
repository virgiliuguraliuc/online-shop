package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.steps.ProductSteps;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnCreatedProduct(){

        productSteps.createProduct();

    }

    @Test(expected = TransactionSystemException.class)
    public void testCreateProduct_whenInvalidRequest_thenThrowException(){
        SaveProductRequest request = new SaveProductRequest();
        //we're not setting any values on the request because we want to send an invalid request
        //we want to be as specific as we can in the exception
        Product product = productService.createProduct(request);

    }

    //all tests should be indepented : daca vreau sa updatez un produs voi crea un produs si il voi updata
    @Test
    public void TestGetProductByID_whenExistingEntity_thenReturnProduct(){
        Product createdProduct = productSteps.createProduct();
        Product retrievedProduct = productService.getProduct(createdProduct.getId());

        assertThat(retrievedProduct, notNullValue());
        assertThat(retrievedProduct.getId(), is(createdProduct.getId()));
        assertThat(retrievedProduct.getName(), is(createdProduct.getName()));
    }
    @Test(expected = ResourceNotFoundException.class)
    public void TestGetProductbyID_whenNonExistingEntity_thenThrowNotFoundException(){
        productService.getProduct(999999);
    }
    @Test
    public void TestUpdateProduct_whenValidRequest_thenReturnUpdatedProduct(){
        Product product =productSteps. createProduct();

        SaveProductRequest request = new SaveProductRequest();
        request.setName(productSteps.createProduct().getName() + " Updated ");
        request.setPrice(productSteps.createProduct().getPrice() + 1000);
        request.setQuantity(productSteps.createProduct().getQuantity() + 1000);
        request.setImagePath(productSteps.createProduct().getImagePath());
        request.setDescription(productSteps.createProduct().getDescription() +"Updated");
        Product updatedProduct = productService.updateProduct(product.getId(), request);

        assertThat(updatedProduct, notNullValue());
        assertThat(updatedProduct.getId(), notNullValue());
        assertThat("unexpected product name",updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
    }

    @Test (expected = ResourceNotFoundException.class)
    public void TestDeletedProduct_whenValidRequest_thenThrowResourceNotFound(){
        Product product = productSteps.createProduct();
        Product product2 = productSteps.createProduct();
        productService.deleteProduct(product.getId());
        productService.getProduct(product.getId());


    }





}
