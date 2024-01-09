package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.ReturnOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnOrderDao extends CrudRepository<ReturnOrder, Long> {
    public List<ReturnOrder> findByProductId(Long productId);
}
