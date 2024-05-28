package com.gabriel.Admin.config;

import com.gabriel.Backend.model.Admin;
import com.gabriel.Backend.model.Roles;
import com.gabriel.Backend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceConfig implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new AdminDetails(admin);
    }


    private List<SimpleGrantedAuthority> convertRolesToAuthorities(List<Roles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
