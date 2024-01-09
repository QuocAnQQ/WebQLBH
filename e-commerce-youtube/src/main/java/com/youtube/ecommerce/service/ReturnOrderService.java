package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dao.CustomerDao;
import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.dao.ReturnOrderDao;
import com.youtube.ecommerce.dto.ReturnOrderDTO;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.ReturnOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReturnOrderService {
    @Autowired
    private ReturnOrderDao returnOrderDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ProductDao productDao;

    public List<ReturnOrderDTO> getAllReturnOrders() {
       List <ReturnOrder> returnOrders= (List<ReturnOrder>) returnOrderDao.findAll();
       List<ReturnOrderDTO> returnOrderDTOS= new ArrayList<>();
       for(ReturnOrder returnOrder : returnOrders){
           ReturnOrderDTO returnOrderDTO= new ReturnOrderDTO();
           returnOrderDTO.setId(returnOrder.getId());
           returnOrderDTO.setDateReturn(returnOrder.getDateReturn());
           returnOrderDTO.setProductId(returnOrder.getProductId());
           Optional<Product> product= productDao.findById(returnOrder.getProductId());
           if(product.isPresent()){
               returnOrderDTO.setProductName(product.get().getProductName());
           }
           returnOrderDTO.setReasonReturn(returnOrder.getReasonReturn());
           returnOrderDTO.setQuantity(returnOrder.getQuantity());
           returnOrderDTO.setCustomerId(returnOrder.getCustomer().getId());
           returnOrderDTO.setCustomerName(returnOrder.getCustomer().getFullName());
           returnOrderDTOS.add(returnOrderDTO);
       }
       return  returnOrderDTOS;
    }

    public ReturnOrder getReturnOrderById(Long returnOrderId) {
        return returnOrderDao.findById(returnOrderId).orElse(null);
    }

    public ReturnOrderDTO createReturnOrder(ReturnOrderDTO dto) {
        // Có thể kiểm tra điều kiện trước khi lưu return order
        ReturnOrder returnOrder = new ReturnOrder();
        returnOrder.setDateReturn(new Date().toInstant());
        returnOrder.setReasonReturn(dto.getReasonReturn());
        returnOrder.setQuantity(dto.getQuantity());
        if(dto.getCustomerId() != null && customerDao.findById(dto.getCustomerId()).isPresent())
        {
            returnOrder.setCustomer(customerDao.findById(dto.getCustomerId()).get());
        }
        if (dto.getProductId() != null){
            Optional<Product> productOptional = productDao.findById(dto.getProductId());
            returnOrder.setProductId(dto.getProductId());
            returnOrder.setProductName(productOptional.get().getProductName());
            if(dto.getQuantity() > 0){
                productOptional.get().setProductQuantity(productOptional.get().getProductQuantity() + dto.getQuantity());
                productDao.save(productOptional.get());
            }
        }
        returnOrderDao.save(returnOrder);
        return dto;
    }

//    public ReturnOrder updateReturnOrder(Long returnOrderId, ReturnOrder updatedReturnOrder) {
//        ReturnOrder existingReturnOrder = returnOrderDao.findById(returnOrderId).orElse(null);
//
//        if (existingReturnOrder != null) {
//            // Cập nhật thông tin của return order
//            existingReturnOrder.setProductCode(updatedReturnOrder.getProductCode());
//            existingReturnOrder.setProductName(updatedReturnOrder.getProductName());
//            existingReturnOrder.setDateReturn(updatedReturnOrder.getDateReturn());
//            existingReturnOrder.setQuantity(updatedReturnOrder.getQuantity());
//            existingReturnOrder.setReasonReturn(updatedReturnOrder.getReasonReturn());
//            // Cập nhật các thuộc tính khác cần thiết
//
//            // Lưu return order đã cập nhật
//            return returnOrderDao.save(existingReturnOrder);
//        }
//
//        return null; // Hoặc có thể throw một exception nếu không tìm thấy return order
//    }

    public void deleteReturnOrder(Long returnOrderId) {
        // Kiểm tra xem return order có tồn tại không trước khi xoá
        returnOrderDao.findById(returnOrderId).ifPresent(returnOrderDao::delete);
    }
}
