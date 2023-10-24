package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.dto.AdminDto;
import com.gabriel.Backend.model.Admin;
import com.gabriel.Backend.repository.AdminRepository;
import com.gabriel.Backend.repository.RoleRepository;
import com.gabriel.Backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
