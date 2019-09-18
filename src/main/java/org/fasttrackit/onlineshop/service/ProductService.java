package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductService.class);

    //IoC = inversion of Control
    private final ProductRepository productRepository;

    //autowired : dependancy injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product createProduct(SaveProductRequest request) {
        LOGGER.info("Creating product: {}", request);

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setImagePath(request.getImagePath());
        product.setQuantity(request.getQuantity());

        return productRepository.save(product);
    }

public Product getProduct(long id) {
    //using Optional
    LOGGER.info("retrieving product {}", id);

        return productRepository.findById(id)
                // lambda expressions
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));

}

public Product updateProduct(long id, SaveProductRequest request) {
      LOGGER.info("updating product {}: {}", id, request);

    Product product = getProduct(id);

    BeanUtils.copyProperties(request, product);

    return  productRepository.save(product);

}
public void deleteProduct(long id){
        LOGGER.info("deleteing product {}", id);
        productRepository.deleteById(id);
}


}