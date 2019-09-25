package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistance.CustomerRepository;

import org.fasttrackit.onlineshop.transfer.customer.SaveCustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CustomerService.class);

    //IoC = inversion of Control
    private final CustomerRepository customerRepository;

    //autowired : dependancy injection
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer createCustomer(SaveCustomerRequest request) {
        LOGGER.info("Creating customer: {}", request);

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());

        return customerRepository.save(customer);
    }

public Customer getCustomer(long id) {
    //using Optional
    LOGGER.info("retrieving customer {}", id);

        return customerRepository.findById(id)
                // lambda expressions
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " not found"));

}

public Customer updateCustomer(long id, SaveCustomerRequest request) {
      LOGGER.info("updating customer {}: {}", id, request);

    Customer customer = getCustomer(id);

    BeanUtils.copyProperties(request, customer);

    return  customerRepository.save(customer);

}


}