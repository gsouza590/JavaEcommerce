package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.model.Country;
import com.gabriel.Backend.repository.CountryRepository;
import com.gabriel.Backend.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;
    @Override
    public List<Country> findAll() {
        return countryRepository.findAll() ;
    }
}
