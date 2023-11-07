package com.gabriel.Backend.service;

import com.gabriel.Backend.dto.CustomerDto;
import com.gabriel.Backend.model.Customer;

public interface CustomerService {
    Customer save(CustomerDto customerDto);

    Customer findByUsername(String username);

    Customer update(CustomerDto customerDto);

    Customer changePass(CustomerDto customerDto);

    CustomerDto getCustomer(String username);
}
