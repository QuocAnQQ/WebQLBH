package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.ReturnOrderDTO;
import com.youtube.ecommerce.entity.ReturnOrder;
import com.youtube.ecommerce.service.ReturnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/return-orders")
public class ReturnOrderController {
        @Autowired
        private ReturnOrderService returnOrderService;

        @GetMapping
        public ResponseEntity<List<ReturnOrderDTO>> getAllReturnOrders() {
        List<ReturnOrderDTO> returnOrders = returnOrderService.getAllReturnOrders();
        return new ResponseEntity<>(returnOrders, HttpStatus.OK);
        }

        @GetMapping("/{returnOrderId}")
        public ResponseEntity<ReturnOrder> getReturnOrderById(@PathVariable Long returnOrderId) {
        ReturnOrder returnOrder = returnOrderService.getReturnOrderById(returnOrderId);

        if (returnOrder != null) {
        return new ResponseEntity<>(returnOrder, HttpStatus.OK);
        } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        }

@PostMapping()
public ResponseEntity<ReturnOrderDTO> createReturnOrder(@RequestBody ReturnOrderDTO returnOrderDTO) {
        ReturnOrderDTO createdReturnOrder = returnOrderService.createReturnOrder(returnOrderDTO);
        return new ResponseEntity<>(createdReturnOrder, HttpStatus.CREATED);
        }

//@PutMapping("/{returnOrderId}")
//public ResponseEntity<ReturnOrder> updateReturnOrder(@PathVariable Long returnOrderId, @RequestBody ReturnOrder updatedReturnOrder) {
//        ReturnOrder returnOrder = returnOrderService.updateReturnOrder(returnOrderId, updatedReturnOrder);
//
//        if (returnOrder != null) {
//        return new ResponseEntity<>(returnOrder, HttpStatus.OK);
//        } else {
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        }

@DeleteMapping("/{returnOrderId}")
public ResponseEntity<Void> deleteReturnOrder(@PathVariable Long returnOrderId) {
        returnOrderService.deleteReturnOrder(returnOrderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

}
