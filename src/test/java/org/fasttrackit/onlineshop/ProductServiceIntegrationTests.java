package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.service.ProductService;
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

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnCreatedProduct(){

        createProduct();

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
        Product createdProduct = createProduct();
        Product retrievedProduct = productService.getProduct(createdProduct.getId());

        assertThat(retrievedProduct, notNullValue());
        assertThat(retrievedProduct.getId(), is(createdProduct.getId()));
        assertThat(retrievedProduct.getName(), is(createdProduct.getName()));
    }
    @Test(expected = ResourceNotFoundException.class)
    public void TestGetProductbyID_whenNonExistingEntity_thenThrowNotFoundException(){
        productService.getProduct(999999);
    }


    private Product createProduct() {
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
