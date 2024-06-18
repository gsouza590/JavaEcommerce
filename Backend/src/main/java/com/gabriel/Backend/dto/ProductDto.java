package com.gabriel.Backend.dto;

import com.gabriel.Backend.model.Category;
import com.gabriel.Backend.model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotEmpty(message = "Nome é necessário")
    private String name;
    @NotEmpty(message = "Descrição é necessário")
    private String description;
    @NotNull(message = "Custo é necessário")
    @Min(value = 0, message = "Custo deve ser maior ou igual a zero")
    private double costPrice;
    @NotNull(message = "Preço de venda é necessário")
    @Min(value = 0, message = "Preço de venda deve ser maior ou igual a zero")
    private double salePrice;
    @NotNull(message = "Quantidade  disponivel é necessária")
    @Min(value = 0, message = "Quantidade  disponivel deve ser maior ou igual a zero")
    private int currentQuantity;
    private Category category;
    private String image;
    private boolean is_activated;
    private boolean is_deleted;
    private String currentPage;

    public static List<ProductDto> ProductToDto(List<Product> products) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }
}