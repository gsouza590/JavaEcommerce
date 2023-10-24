package com.gabriel.Backend.service;

import com.gabriel.Backend.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Category save(Category category);

    Optional<Category> findById(Long Id);

    Category update(Category category);

    void deleteById(Long id);

    void enabledById(Long id);

    List<Category> findAllByActivated();
}
