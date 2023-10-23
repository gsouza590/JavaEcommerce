package com.gabriel.Admin.config;



import com.gabriel.Backend.model.Admin;
import com.gabriel.Backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

import java.util.stream.Collectors;

public class AdminServiceConfig implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByUsername(username);
        if(admin==null){
            throw new UsernameNotFoundException("Não foi possível encontrar o usuário");
        }
        return new User(
                admin.getUsername(),
                admin.getPassword(),
                admin.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toList()));
    }
}
