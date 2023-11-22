package com.gabriel.Backend.service;

import com.gabriel.Backend.dto.AdminDto;
import com.gabriel.Backend.model.Admin;

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin findByUsername(String username);

    AdminDto getCustomer(String name);

    Admin  update(AdminDto adminDto);
}
