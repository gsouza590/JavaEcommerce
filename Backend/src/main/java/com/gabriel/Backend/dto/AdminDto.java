package com.gabriel.Backend.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    @Size(min = 3, max = 10, message = "Deve conter entre 3-10 caracteres")
    private String firstName;
    @Size(min = 3, max = 10, message = "Deve conter entre 3-10 caracteres")
    private String lastName;
    private String username;
    @Size(min = 5, max = 10, message = "Senha deve conter entre 6-15 caracteres")
    private String password;
    private String repeatPassword;
}
