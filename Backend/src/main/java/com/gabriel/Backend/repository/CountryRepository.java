package com.gabriel.Backend.repository;

import com.gabriel.Backend.model.City;
import com.gabriel.Backend.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
