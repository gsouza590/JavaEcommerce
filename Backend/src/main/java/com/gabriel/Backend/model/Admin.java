package com.gabriel.Backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    private Long id;
    private String firstName;
    private String LastName;
    private String username;
    private String password;
    private String image;

    private List<Roles> roles;
}
