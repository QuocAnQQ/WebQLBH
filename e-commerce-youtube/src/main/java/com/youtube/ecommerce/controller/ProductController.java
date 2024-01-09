package com.youtube.ecommerce.controller;

import com.youtube.ecommerce.dto.*;
import com.youtube.ecommerce.entity.Product;

import com.youtube.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;



//    @PreAuthorize("hasRole('')")
    @PreAuthorize("permitAll()")
    @PostMapping(value = {"/addNewProduct"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product addNewProduct(@ModelAttribute @RequestBody ProductDTO productDTO
                                ) throws Exception {
        System.out.println("ághdjk"+ productDTO);

            return productService.addNewProduct(productDTO);


    }


    @PreAuthorize("permitAll()")
    @GetMapping({"/getAllProducts"})
    public List<ProductDetailDtO> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "") String searchKey) {
        List<ProductDetailDtO> result = productService.getAllProducts(pageNumber, searchKey);
        System.out.println("Result size is "+ result.size());
        return result;
    }

    @GetMapping({"/getProductDetailsById/{productId}"})
    public Product getProductDetailsById(@PathVariable("productId") Long productId) {
        return productService.getProductDetailsById(productId);
    }

//    @PreAuthorize("hasRole('Admin')")
    @PreAuthorize("permitAll()")
    @DeleteMapping({"/deleteProductDetails/{id}"})
    public void deleteProductDetails(@PathVariable("id") Long id) {
        productService.deleteProductDetails(id);
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping({"/getProductDetails/{isSingleProductCheckout}/{productId}"})
    public List<Product> getProductDetails(@PathVariable(name = "isSingleProductCheckout" ) boolean isSingleProductCheckout,
                                           @PathVariable(name = "productId")  Long productId) {
        return productService.getProductDetails(isSingleProductCheckout, productId);
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/editProduct")
    public ProductUpdateDTO editProductDTO(@RequestBody ProductUpdateDTO productUpdateDTO){
        return  productService.editProduct(productUpdateDTO);
    }
    @PreAuthorize("permitAll()")
    @GetMapping ("/getProductByIdCategory/{id}")
    public List<ProductUpdateDTO> getProductDTOByIdCategory(@PathVariable("id") Long id){
        return  productService.getByIdProductCategory(id);
    }
    @PreAuthorize("hasRole('Admin')")
    @PostMapping({"/getProductStatistics"})
    public List<StatisticsDTO> getProductDetailsById(@RequestBody FilterDTO filterDTO) {
        if(filterDTO.getMonth() == null){ filterDTO.setMonth("default");}
        return productService.productStatistics(filterDTO);
    }


}
