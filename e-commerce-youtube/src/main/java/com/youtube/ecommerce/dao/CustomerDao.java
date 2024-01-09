package com.youtube.ecommerce.dao;

import com.youtube.ecommerce.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends CrudRepository<Customer, Long> {
    public Customer findByPhoneNumber(String phone_number);
}
