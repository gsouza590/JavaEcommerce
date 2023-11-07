package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.dto.ProductDto;
import com.gabriel.Backend.model.Product;
import com.gabriel.Backend.repository.ProductRepository;
import com.gabriel.Backend.service.ProductService;
import com.gabriel.Backend.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ImageUpload imageUpload;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return ProductDto.ProductToDto(products);
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = modelMapper.map(productDto, Product.class);

            // Tratar o upload da imagem
            if (imageProduct != null) {
                if (imageUpload.uploadImage(imageProduct)) {
                    System.out.println("Upload realizado com sucesso");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.set_activated(true);
            product.set_deleted(false);

            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = productRepository.getReferenceById(productDto.getId());

            modelMapper.map(productDto, product);

            if (imageProduct != null) {
                if (!imageUpload.checkExisted(imageProduct)) {
                    System.out.println("Upload realizado com sucesso");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }

            return productRepository.save(product);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.set_deleted(true);
        product.set_activated(false);
        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.set_activated(true);
        product.set_deleted(false);
        productRepository.save(product);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getReferenceById(id);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> randomProduct() {
        return ProductDto.ProductToDto(productRepository.randomProduct());
    }
    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        List<Product> products = productRepository.findAllByNameOrDescription(keyword);
        List<ProductDto> productDtoList = ProductDto.ProductToDto(products);
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<ProductDto> dtoPage = toPage(productDtoList, pageable);
        return dtoPage;
    }

    @Override
    public Page<ProductDto> findAllProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> productDtoLists = this.findAll();
        Page<ProductDto> productDtoPage = toPage(productDtoLists, pageable);
        return productDtoPage;
    }



    private Page toPage(List list, Pageable pageable) {
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), list.size());

        if (startIndex >= endIndex) {
            return Page.empty();
        }

        return new PageImpl<>(list.subList(startIndex, endIndex), pageable, list.size());
    }

}