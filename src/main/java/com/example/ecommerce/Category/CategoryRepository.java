package com.example.ecommerce.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends CrudRepository<CategoryModel, Long> {

    @Query(value = "SELECT * FROM category WHERE name=:name", nativeQuery = true)
    CategoryModel findByName(@Param("name") String name);

    @Query(value = "SELECT id,name,description,parent_id FROM category WHERE id=:id", nativeQuery = true)
    Optional<CategoryModel> findCategoryModel(@Param("id") Long id);
}
