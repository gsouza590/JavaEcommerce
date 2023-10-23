package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.model.Admin;
import com.gabriel.Backend.repository.AdminRepository;
import com.gabriel.Backend.repository.RoleRepository;
import com.gabriel.Backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
