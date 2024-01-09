package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.CategoryDTO;
import com.youtube.ecommerce.entity.ProductCategory;
import com.youtube.ecommerce.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategories();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for(ProductCategory productCategory : productCategories){
            CategoryDTO categoryDTO = new CategoryDTO(productCategory.getId(), productCategory.getProductCategoryCode(),productCategory.getProductCategoryName());
            categoryDTOs.add(categoryDTO);
        }
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/{productCategoryId}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable Long productCategoryId) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(productCategoryId);

        if (productCategory != null) {
            return new ResponseEntity<>(productCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody ProductCategory productCategory) {
        ProductCategory createdProductCategory = productCategoryService.createProductCategory(productCategory);
        return new ResponseEntity<>(createdProductCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{productCategoryId}")
    public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable Long productCategoryId, @RequestBody ProductCategory updatedProductCategory) {
        ProductCategory productCategory = productCategoryService.updateProductCategory(productCategoryId, updatedProductCategory);

        if (productCategory != null) {
            return new ResponseEntity<>(productCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{productCategoryId}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long productCategoryId) {
        productCategoryService.deleteProductCategory(productCategoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
