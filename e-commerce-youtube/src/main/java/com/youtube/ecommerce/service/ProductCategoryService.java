package com.youtube.ecommerce.service;

import com.youtube.ecommerce.dao.ProductCategoryDao;
import com.youtube.ecommerce.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    public List<ProductCategory> getAllProductCategories() {
        return (List<ProductCategory>) productCategoryDao.findAll();
    }

    public ProductCategory getProductCategoryById(Long productCategoryId) {
        return productCategoryDao.findById(productCategoryId).orElse(null);
    }

    public ProductCategory createProductCategory(ProductCategory productCategory) {
        // Có thể kiểm tra điều kiện trước khi lưu product category
        return productCategoryDao.save(productCategory);
    }

    public ProductCategory updateProductCategory(Long productCategoryId, ProductCategory updatedProductCategory) {
        ProductCategory existingProductCategory = productCategoryDao.findById(productCategoryId).orElse(null);

        if (existingProductCategory != null) {
            // Cập nhật thông tin của product category
            existingProductCategory.setProductCategoryCode(updatedProductCategory.getProductCategoryCode());
            existingProductCategory.setProductCategoryName(updatedProductCategory.getProductCategoryName());
            // Cập nhật các thuộc tính khác cần thiết

            // Lưu product category đã cập nhật
            return productCategoryDao.save(existingProductCategory);
        }

        return null; // Hoặc có thể throw một exception nếu không tìm thấy product category
    }

    public void deleteProductCategory(Long productCategoryId) {
        // Kiểm tra xem product category có tồn tại không trước khi xoá
        productCategoryDao.findById(productCategoryId).ifPresent(productCategory -> {
            // Xoá tất cả sản phẩm thuộc product category trước khi xoá product category
            productCategory.getProducts().forEach(product -> product.setProductCategory(null));

            productCategoryDao.delete(productCategory);
        });
    }
}
