package com.gabriel.Backend.service;

import com.gabriel.Backend.dto.AdminDto;
import com.gabriel.Backend.model.Admin;
import org.springframework.transaction.annotation.Transactional;

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin findByUsername(String username);

    AdminDto getAdmin(String username);

    Admin  update(AdminDto adminDto);


}
