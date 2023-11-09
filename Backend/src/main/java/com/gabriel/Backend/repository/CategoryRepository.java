package com.gabriel.Backend.repository;

import com.gabriel.Backend.dto.CategoryDto;
import com.gabriel.Backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.is_activated= true and c.is_deleted=false")
    List<Category> findAllByActivated();


    @Query(value = "select new com.gabriel.Backend.dto.CategoryDto(c.id, c.name, count(p.category.id)) " +
            "from Category c left join Product p on c.id = p.category.id " +
            "where c.is_activated = true and c.is_deleted = false " +
            "group by c.id ")
    List<CategoryDto> getCategoriesAndSize();
}
