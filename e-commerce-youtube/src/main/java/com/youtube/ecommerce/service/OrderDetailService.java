package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dao.*;
import com.youtube.ecommerce.dto.OrderDTO;
import com.youtube.ecommerce.entity.*;
import com.youtube.ecommerce.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public OrderDTO Create (OrderDTO orderDTO) {

        OrderDetail entity = new OrderDetail();
        entity.setQuantity(orderDTO.getQuantity());
        entity.setPrice(orderDTO.getPrice());
        entity.setTotalPrice(orderDTO.getQuantity()*orderDTO.getPrice());
        entity.setTimeSale(orderDTO.getTimeSale());
         Optional<ProductCategory> productCategory= productCategoryDao.findById(orderDTO.getCategoryId());
         if(productCategory.isPresent()){
             entity.setProductCategoryId(productCategory.get().getId());
         }
        Optional<Product> product= productDao.findById(orderDTO.getProductId());
        if(product.isPresent()){
            entity.setProduct(product.get());
            entity.setProductName(product.get().getProductName());
            if(product.get().getProductQuantity() >= orderDTO.getQuantity()){
                product.get().setProductQuantity(product.get().getProductQuantity() - orderDTO.getQuantity());
                productDao.save(product.get());
            }

        }
         Instant now= Instant.now();
         entity.setTimeSale(now);

        Customer response = customerDao.findByPhoneNumber(orderDTO.getCustomerPhone());
        if(response == null){
            Customer customer = new Customer();
            customer.setFullName(orderDTO.getCustomerName());
            customer.setPhoneNumber(orderDTO.getCustomerPhone());
            response = customerDao.save(customer);
        }
        entity.setCustomer(response);
        Optional<String> currentUsername = SecurityUtils.getCurrentUserLogin();
        if(currentUsername.isPresent()){
            Optional<User>user= userDao.findByUserName(currentUsername.get());
           if(user.isPresent()){
               entity.setUser(user.get());
           }
        }
        orderDetailDao.save(entity);
        return orderDTO;
    }

    public List<OrderDTO> getAllOrder(){
        List<OrderDetail> orders= (List<OrderDetail>) orderDetailDao.findAll();
        List<OrderDTO> orderDTOList= new ArrayList<>();
        for(OrderDetail orderDetail :orders){
            OrderDTO orderDTO= new OrderDTO();
            orderDTO.setId(orderDetail.getId());
            orderDTO.setProductName(orderDetail.getProductName());
            orderDTO.setPrice(orderDetail.getPrice());
            orderDTO.setQuantity(orderDetail.getQuantity());
            orderDTO.setTotalPrice(orderDetail.getTotalPrice());
            orderDTO.setCustomerName(orderDetail.getCustomer().getFullName());
            orderDTOList.add(orderDTO);
        }
        return  orderDTOList;

    }
    public  void deleteById(Long id){
        this.orderDetailDao.deleteById(id);
    }
}
