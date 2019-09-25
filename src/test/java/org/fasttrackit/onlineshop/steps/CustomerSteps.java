package org.fasttrackit.onlineshop.steps;

import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.service.CustomerService;
import org.fasttrackit.onlineshop.transfer.customer.SaveCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class CustomerSteps {

    @Autowired
    private CustomerService customerService;


    private Customer createCustomer() {
        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setFirstName("Customer_first_name");
        request.setLastName("customer_last_name");

        Customer customer = customerService.createCustomer(request);

        assertThat(customer, notNullValue());
        assertThat(customer.getId(), notNullValue());
        assertThat(customer.getId(), greaterThan((0L)));
        assertThat("unexpected product name",customer.getFirstName(), is(request.getFirstName()));
        //we can specify the reason for the failure of the test like above
        assertThat(customer.getLastName(), is(request.getLastName()));


        return customer;}



}
