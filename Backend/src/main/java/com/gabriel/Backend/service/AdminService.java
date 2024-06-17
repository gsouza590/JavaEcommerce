package com.gabriel.Backend.service;

import com.gabriel.Backend.dto.AdminDto;
import com.gabriel.Backend.model.Admin;
import org.springframework.transaction.annotation.Transactional;

public interface AdminService {
    Admin save(AdminDto adminDto);

    Admin findByUsername(String username);

<<<<<<< HEAD
    AdminDto getAdmin(String username);
=======
    AdminDto getAdmin(String name);
>>>>>>> a972b13e25d0fadc3c043290cc30af4ba9281dc4

    Admin  update(AdminDto adminDto);


    boolean existsByUsername(String username);
}
