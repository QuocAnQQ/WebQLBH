package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryDao extends CrudRepository<ProductCategory, Long> {
}
