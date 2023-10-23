package com.gabriel.Backend.service;

import com.gabriel.Backend.model.Admin;

public interface AdminService {

    Admin findByUsername(String username);

}
