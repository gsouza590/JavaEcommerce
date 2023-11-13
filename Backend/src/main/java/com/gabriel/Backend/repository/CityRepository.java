package com.gabriel.Backend.repository;

import com.gabriel.Backend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
