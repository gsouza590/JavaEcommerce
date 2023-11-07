package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.dto.CustomerDto;
import com.gabriel.Backend.model.Customer;
import com.gabriel.Backend.repository.CustomerRepository;
import com.gabriel.Backend.repository.RoleRepository;
import com.gabriel.Backend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer update(CustomerDto dto) {
        Customer customer = modelMapper.map(dto, Customer.class);
        return customerRepository.save(customer);
    }

    @Override
    public CustomerDto getCustomer(String username) {
        Customer customer = customerRepository.findByUsername(username);
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public Customer changePass(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customerRepository.save(customer);
    }
}

