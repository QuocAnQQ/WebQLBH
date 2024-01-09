package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.ImageModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageModelDao extends CrudRepository<ImageModel, Long> {
}
