package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.dto.CustomerDto;
import com.gabriel.Backend.model.Customer;
import com.gabriel.Backend.repository.CustomerRepository;
import com.gabriel.Backend.repository.RoleRepository;
import com.gabriel.Backend.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Customer save(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer update(CustomerDto dto) {
        Customer customer = customerRepository.findByUsername(dto.getUsername());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer.setCountry(dto.getCountry());
        customer.setPhoneNumber(dto.getPhoneNumber());
        return customerRepository.save(customer);
    }



    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomer(String username) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null) {
            return modelMapper.map(customer, CustomerDto.class);
        }
        return null;
    }

    @Override
    @Transactional
    public Customer changePass(CustomerDto customerDto) {
        Customer customer = customerRepository.findByUsername(customerDto.getUsername());
        if (customer != null) {
            customer.setPassword(customerDto.getPassword());
            return customerRepository.save(customer);
        }
        return null;
    }
}
