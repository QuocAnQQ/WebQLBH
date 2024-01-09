package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dao.CustomerDao;
import com.youtube.ecommerce.dto.CutomerDTO;
import com.youtube.ecommerce.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerDao customerDao;
    public List<CutomerDTO> getAllCustomers() {
        List<Customer> customers=(List<Customer>) customerDao.findAll();
        List<CutomerDTO> cutomerDTOS= new ArrayList<>();
        for(Customer customer: customers){
            CutomerDTO cutomerDTO= new CutomerDTO();
            cutomerDTO.setId(customer.getId());
            cutomerDTO.setFullName(customer.getFullName());
            cutomerDTO.setPhoneNumber(customer.getPhoneNumber());
            cutomerDTOS.add(cutomerDTO);
        }
        return cutomerDTOS;
    }

    public Customer getCustomerById(Long customerId) {
        return customerDao.findById(customerId).orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        // Có thể kiểm tra điều kiện trước khi lưu customer
        return customerDao.save(customer);
    }

    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerDao.findById(customerId).orElse(null);

        if (existingCustomer != null) {
            // Cập nhật thông tin của customer
            existingCustomer.setFullName(updatedCustomer.getFullName());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            // Cập nhật các thuộc tính khác cần thiết

            // Lưu customer đã cập nhật
            return customerDao.save(existingCustomer);
        }

        return null; // Hoặc có thể throw một exception nếu không tìm thấy customer
    }

    public void deleteCustomer(Long customerId) {
        // Kiểm tra xem customer có tồn tại không trước khi xoá
        customerDao.findById(customerId).ifPresent(customer -> {
            // Xoá tất cả orderDetails và returnOrders liên quan trước khi xoá customer
            customer.getOrderDetails().forEach(orderDetail -> orderDetail.setCustomer(null));
            customer.getReturnOrders().forEach(returnOrder -> returnOrder.setCustomer(null));

            customerDao.delete(customer);
        });
    }

}
