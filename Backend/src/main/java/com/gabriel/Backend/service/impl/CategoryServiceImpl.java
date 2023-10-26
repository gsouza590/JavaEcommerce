package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.model.Category;
import com.gabriel.Backend.repository.CategoryRepository;
import com.gabriel.Backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Category save(Category category) {
        category.set_activated(true);
        category.set_deleted(false);
        return repository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return repository.findById(id).get();
    }


    @Override
    public Category update(Category category) {
        Category categoryUpdate = repository.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        return repository.save(categoryUpdate);
    }


    @Override
    public void deleteById(Long id) {
        Category category = repository.getById(id);
        category.set_activated(false);
        category.set_deleted(true);
        repository.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = repository.getById(id);
        category.set_activated(true);
        category.set_deleted(false);
        repository.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return repository.findAllByActivated();
    }
}
