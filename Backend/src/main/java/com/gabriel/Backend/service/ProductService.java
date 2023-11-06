package com.gabriel.Backend.service;

import com.gabriel.Backend.dto.ProductDto;
import com.gabriel.Backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    Product save(MultipartFile imageProduct, ProductDto productDto);

    Product update(MultipartFile imageProduct, ProductDto productDto);

    void deleteById(Long Id);

    void enableById(Long Id);

    ProductDto getById(Long id);

    List<ProductDto> randomProduct();

    Page<ProductDto> searchProducts(int pageNo, String keyword);

    Page<ProductDto> findAllProducts(int pageNo);
}
