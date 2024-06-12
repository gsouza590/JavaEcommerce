package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.dto.AdminDto;
import com.gabriel.Backend.model.Admin;
import com.gabriel.Backend.repository.AdminRepository;
import com.gabriel.Backend.repository.RoleRepository;
import com.gabriel.Backend.service.AdminService;
import com.gabriel.Backend.service.exceptions.Admin.AdminAlreadyExistsException;
import com.gabriel.Backend.service.exceptions.Admin.AdminNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;

    @Override
    public Admin save(AdminDto adminDto) {
        if (adminRepository.findByUsername(adminDto.getUsername()) != null) {
            throw new AdminAlreadyExistsException("Admin with username " + adminDto.getUsername() + " already exists");
        }
        Admin admin = convertToEntity(adminDto);
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public Admin findByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);

        if (admin == null) {
            throw new AdminNotFoundException("Admin with username " + username + " not found");
        }

        return adminRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public AdminDto getAdmin(String name) {
        Admin admin = adminRepository.findByUsername(name);
        if (admin == null) {
            throw new AdminNotFoundException("Admin with username " + name + " not found");
        }
        return convertToDto(admin);

    }

    @Override
    @Transactional
    public Admin update(AdminDto adminDto) {
        Admin admin = adminRepository.findByUsername(adminDto.getUsername());
        if (admin == null) {
            throw new AdminNotFoundException("Admin with username " + adminDto.getUsername() + " not found");
        }

        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        return adminRepository.save(admin);

    }

    private Admin convertToEntity(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        return admin;
    }

    private AdminDto convertToDto(Admin admin) {
        AdminDto adminDto = new AdminDto();
        adminDto.setFirstName(admin.getFirstName());
        adminDto.setLastName(admin.getLastName());
        adminDto.setUsername(admin.getUsername());
        adminDto.setPassword(admin.getPassword());
        return adminDto;
    }
}
