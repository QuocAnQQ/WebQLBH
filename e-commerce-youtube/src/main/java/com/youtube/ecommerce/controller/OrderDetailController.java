package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.OrderDTO;
import com.youtube.ecommerce.entity.OrderDetail;
import com.youtube.ecommerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

//    @PreAuthorize("hasRole('User')")
    @PostMapping({"/addOrderDetail"})
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO response = orderDetailService.Create(orderDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('User')")
    @GetMapping({"/getOrderDetails"})
    public List<OrderDTO> getOrderDetails() {
        return orderDetailService.getAllOrder();
    }
//    @PreAuthorize("hasRole('User')")
    @DeleteMapping({"/deleteOrderDetail/{id}"})
    public void getOrderDetails(@PathVariable("id") Long id) {
         orderDetailService.deleteById(id);
    }

//
//    @PreAuthorize("hasRole('Admin')")
//    @GetMapping({"/getAllOrderDetails/{status}"})
//    public List<OrderDetail> getAllOrderDetails(@PathVariable(name = "status") String status) {
//        return orderDetailService.getAllOrderDetails(status);
//    }
//
//    @PreAuthorize("hasRole('Admin')")
//    @GetMapping({"/markOrderAsDelivered/{orderId}"})
//    public void markOrderAsDelivered(@PathVariable(name = "orderId") Integer orderId) {
//        orderDetailService.markOrderAsDelivered(orderId);
//    }
//
//    @PreAuthorize("hasRole('User')")
//    @GetMapping({"/createTransaction/{amount}"})
//    public TransactionDetails createTransaction(@PathVariable(name = "amount") Double amount) {
//        return orderDetailService.createTransaction(amount);
//    }
}
