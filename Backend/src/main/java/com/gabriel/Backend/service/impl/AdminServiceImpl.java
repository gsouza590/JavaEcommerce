package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.dto.AdminDto;
import com.gabriel.Backend.model.Admin;
import com.gabriel.Backend.repository.AdminRepository;
import com.gabriel.Backend.repository.RoleRepository;
import com.gabriel.Backend.service.AdminService;
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
        Admin admin = convertToEntity(adminDto);
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public AdminDto getAdmin(String name) {
        Admin admin = adminRepository.findByUsername(name);
        return convertToDto(admin);
    }

    @Override
    @Transactional
    public Admin update(AdminDto adminDto) {
        Admin admin = adminRepository.findByUsername(adminDto.getUsername());
        if (admin != null) {
            admin.setFirstName(adminDto.getFirstName());
            admin.setLastName(adminDto.getLastName());
            admin.setPassword(adminDto.getPassword());
            // Atualize outros campos conforme necessário
            return adminRepository.save(admin);
        }
        return null;
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
