package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.model.City;
import com.gabriel.Backend.repository.CityRepository;
import com.gabriel.Backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository repository;
    @Override
    public List<City> findAll() {
        return repository.findAll();
    }
}
