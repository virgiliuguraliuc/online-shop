package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.product.GetProductsRequest;
import org.fasttrackit.onlineshop.transfer.product.ProductResponse;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
@Transactional
public Page<ProductResponse> getProducts (GetProductsRequest request, Pageable pageable){
    LOGGER.info("retrieving products: {}", request);

   Page<Product> products;

    if(request != null && request.getPartialName() != null && request.getMinimumQuantity() != null) {
       products = productRepository.findByNameContainingAndQuantityGreaterThanEqual(request.getPartialName(), request.getMinimumQuantity(), pageable);
    }
    else if (request != null && request.getPartialName() != null){
        products = productRepository.findByNameContaining(request.getPartialName(), pageable);
    }
    else{
        products = productRepository.findAll(pageable);
    }
    List<ProductResponse> productResponses = new ArrayList<>();
    for (Product product: products.getContent()) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setDescription(product.getDescription());
        productResponse.setName(product.getName());
        productResponse.setImagePath(product.getImagePath());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());

        productResponses.add(productResponse);

    }

    return new PageImpl<>(productResponses, pageable, products.getTotalElements());
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