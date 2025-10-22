package com.manager.repository;

import com.manager.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Short> {
    @Override
    List<Categories> findAll();
}
