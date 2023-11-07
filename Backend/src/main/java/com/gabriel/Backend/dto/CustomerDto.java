package com.gabriel.Backend.dto;

import com.gabriel.Backend.model.City;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @Size(min = 3, max = 10, message = "Nome deve conter 3 a 10 caracteres")
    private String firstName;

    @Size(min = 3, max = 10, message = "Sobrenome deve conter 3 a 10 caracteres")
    private String lastName;
    private String username;
    @Size(min = 6, max = 15, message = "Senha deve conter 6 a 15 caracteres")
    private String password;

    @Size(min = 10, max = 15, message = "Telefone deve conter 3 a 10 caracteres")
    private String phoneNumber;

    private String address;
    private String repeatPassword;
    private City city;
    private String image;
    private String country;
}