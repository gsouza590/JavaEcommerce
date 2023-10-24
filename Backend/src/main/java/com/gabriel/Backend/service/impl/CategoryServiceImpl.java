package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.model.Category;
import com.gabriel.Backend.repository.CategoryRepository;
import com.gabriel.Backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    @Autowired

    private CategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category();
        categorySave.setName(category.getName());
        categorySave.set_activated(true);
        categorySave.set_deleted(false);
        return repository.save(categorySave);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdated = repository.getReferenceById(category.getId());
        categoryUpdated.setName(category.getName());
        return repository.save(categoryUpdated);
    }

    @Override
    public void deleteById(Long id) {
        Category category = repository.getReferenceById(id);
        category.set_activated(false);
        category.set_deleted(true);
        repository.save(category);
    }

    @Override
    public void enabledById(Long id) {
        Category category = repository.getReferenceById(id);
        category.set_activated(true);
        category.set_deleted(false);
        repository.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return repository.findByAllActivated();
    }
}
