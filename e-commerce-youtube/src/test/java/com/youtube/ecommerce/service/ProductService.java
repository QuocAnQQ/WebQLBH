package com.youtube.ecommerce.service;
import com.youtube.ecommerce.dto.ProductDetailDtO;
import com.youtube.ecommerce.dao.ProductCategoryDao;
import com.youtube.ecommerce.dao.ProductDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.dto.ProductDTO;
import com.youtube.ecommerce.entity.Product;
import com.youtube.ecommerce.entity.ProductCategory;
import com.youtube.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private FileService fileService;


    @Autowired
    private UserDao userDao;


    private  String productDirectory="D:/files/product";
    public Product addNewProduct(ProductDTO productDTO) throws Exception {
        Product product= new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductQuantity(productDTO.getProductQuantity());
        product.setProductDescription(productDTO.getProductDescription());
        product.setProductActualPrice(productDTO.getProductActualPrice());
        product.setProductPriceInput(productDTO.getProductPriceInput());
        Optional<ProductCategory> productCategory= productCategoryDao.findById((productDTO.getProductCategoryId()));
        if(productCategory.isPresent()){
        product.setProductCategory(productCategory.get());
            System.out.println("vao đây chưa");
        }else{
            throw  new Exception("Không tìm thấy id của product category tương ứng");
        }
        if (!productDTO.getProductImages().isEmpty()) {
            String productImagePath = fileService.saveFile(productDTO.getProductImages(), productDirectory);
            product.setProductImages(productImagePath);
        }

    return productDao.save(product);
    }

    public List<ProductDetailDtO> getAllProducts(int pageNumber, String searchKey) {
        Pageable pageable = PageRequest.of(pageNumber,12);

        if(searchKey.equals("")) {
            List<Product> products=productDao.findAll(pageable);
            List<ProductDetailDtO> productDetailDTOList = new ArrayList<>();

            for (Product product : products) {
                ProductDetailDtO productDetailDTO = new ProductDetailDtO();
                productDetailDTO.setProductName(product.getProductName());
                productDetailDTO.setProductQuantity(product.getProductQuantity());
                productDetailDTO.setProductDescription(product.getProductDescription());
                productDetailDTO.setProductPriceInput(product.getProductPriceInput());
                productDetailDTO.setProductActualPrice(product.getProductActualPrice());
                productDetailDTO.setProductCategoryId(product.getProductCategory().getId());

                // Chuyển đổi dữ liệu ảnh thành đường dẫn (URL)
                String imageFileName = "product/image/" + product.getProductImages();
                productDetailDTO.setProductImageURL(imageFileName);

                productDetailDTOList.add(productDetailDTO);
            }
            return  productDetailDTOList;
        } else {
      List<Product> products= productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                    searchKey, searchKey, pageable
            );
            List<ProductDetailDtO> productDetailDTOList = new ArrayList<>();

            for (Product product : products) {
                ProductDetailDtO productDetailDTO = new ProductDetailDtO();
                productDetailDTO.setProductName(product.getProductName());
                productDetailDTO.setProductQuantity(product.getProductQuantity());
                productDetailDTO.setProductDescription(product.getProductDescription());
                productDetailDTO.setProductPriceInput(product.getProductPriceInput());
                productDetailDTO.setProductActualPrice(product.getProductActualPrice());
                productDetailDTO.setProductCategoryId(product.getProductCategory().getId());

                // Chuyển đổi dữ liệu ảnh thành đường dẫn (URL)
                String imageFileName = "product/image/" + product.getProductImages();
                productDetailDTO.setProductImageURL(imageFileName);

                productDetailDTOList.add(productDetailDTO);
            }
            return  productDetailDTOList;

        }

    }

    public Product getProductDetailsById(Long productId) {
        return productDao.findById(productId).get();
    }

    public void deleteProductDetails(Long productId) {
        productDao.deleteById(productId);
    }

    public List<Product> getProductDetails(boolean isSingleProductCheckout, Long productId) {
//        if(isSingleProductCheckout && productId != 0) {
            // we are going to buy a single product

            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
//        } else {
//            // we are going to checkout entire cart
//            String username = JwtRequestFilter.CURRENT_USER;
//            User user = userDao.findById(username).get();
//            return  null;
//            List<Cart> carts = cartDao.findByUser(user);
//
//            return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());
        }
    }

