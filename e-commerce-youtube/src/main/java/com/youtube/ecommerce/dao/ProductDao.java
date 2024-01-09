package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends CrudRepository<Product, Long> {
    public List<Product> findAll(Pageable pageable);

    public List<Product> findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
            String key1, String key2, Pageable pageable
    );
    List<Product> findByProductCategory_Id(Long productCategoryId);
}
