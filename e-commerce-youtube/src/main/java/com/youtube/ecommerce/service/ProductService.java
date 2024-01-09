package com.youtube.ecommerce.service;
import com.youtube.ecommerce.dao.*;
import com.youtube.ecommerce.dto.*;
import com.youtube.ecommerce.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;
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
 private ReturnOrderDao returnOrderDao;
@Autowired
private OrderDetailDao orderDetailDao;

    @Autowired
    private UserDao userDao;


    private  String productDirectory="D:/files/product";
 // Giả sử tên tệp đã được lưu trong cơ sở dữ liệu


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
                productDetailDTO.setId(product.getId());
                productDetailDTO.setProductName(product.getProductName());
                productDetailDTO.setProductQuantity(product.getProductQuantity());
                productDetailDTO.setProductDescription(product.getProductDescription());
                productDetailDTO.setProductPriceInput(product.getProductPriceInput());
                productDetailDTO.setProductActualPrice(product.getProductActualPrice());
                productDetailDTO.setProductCategoryId(product.getProductCategory().getId());
                String imageFileName = product.getProductImages();
                String imageUrl = productDirectory + "/" + imageFileName;
                // Chuyển đổi dữ liệu ảnh thành đường dẫn (URL)

                productDetailDTO.setProductImageURL(imageUrl);

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
                productDetailDTO.setId(product.getId());
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

    public void deleteProductDetails(Long  productId) {
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
    public ProductUpdateDTO editProduct(ProductUpdateDTO productUpdateDTO){

        Optional<Product> product1= productDao.findById(productUpdateDTO.getId());
        if(product1.isPresent()){
            Product product= product1.get();
            product.setProductName(productUpdateDTO.getProductName());
            product.setProductQuantity(productUpdateDTO.getProductQuantity());
            product.setProductDescription(productUpdateDTO.getProductDescription());
            product.setProductActualPrice(productUpdateDTO.getProductActualPrice());
            productDao.save(product);
        }
        return  productUpdateDTO;

    }
    public  List<ProductUpdateDTO> getByIdProductCategory( Long id){
        List<Product> products= productDao.findByProductCategory_Id(id);
        List<ProductUpdateDTO> productUpdateDTOList= new ArrayList<>();
        for(Product product: products){
            ProductUpdateDTO productUpdateDTO= new ProductUpdateDTO();
            productUpdateDTO.setId(product.getId());
            productUpdateDTO.setProductName(product.getProductName());
            productUpdateDTO.setProductQuantity(product.getProductQuantity());
            productUpdateDTO.setProductDescription(product.getProductDescription());
            productUpdateDTO.setProductActualPrice(product.getProductActualPrice());
            productUpdateDTOList.add(productUpdateDTO);
        }
        return  productUpdateDTOList;


    }

    public List<StatisticsDTO> productStatistics (FilterDTO filterDTO){
        List<StatisticsDTO> statisticsDTOList = new ArrayList<>();

        Long monthNumber = convertMonthStringToNumber(filterDTO.getMonth());
        if (monthNumber != -1 && filterDTO.getProductId() != null) {
            List<OrderDetail> resultOrder = orderDetailDao.findByProductId(filterDTO.getProductId());
            Map<Long, List<OrderDetail>> groupedByProduct = resultOrder.stream()
                    .collect(Collectors.groupingBy(orderDetail -> orderDetail.getProduct().getId()));

            groupedByProduct.forEach((productId, orderList) -> {
                StatisticsDTO statisticsDTO1 = new StatisticsDTO();
                statisticsDTO1.setSoldQuantity(0);
                statisticsDTO1.setProductId(productId);
                statisticsDTO1.setProductName(productDao.findById(productId).get().getProductName());
                statisticsDTO1.setProductActualPrice(productDao.findById(productId).get().getProductActualPrice());
                statisticsDTO1.setProductPriceInput(productDao.findById(productId).get().getProductPriceInput());
                orderList.forEach(orderDetail -> {
                    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(orderDetail.getTimeSale(), java.time.ZoneId.systemDefault());
                    int monthOfDate = zonedDateTime.getMonthValue();
                    if (monthOfDate == monthNumber) {
                        statisticsDTO1.setSoldQuantity(statisticsDTO1.getSoldQuantity() + orderDetail.getQuantity());
                    }
                });
                statisticsDTOList.add(statisticsDTO1);
            });

            // list return
            List<ReturnOrder> returnOrder = returnOrderDao.findByProductId(filterDTO.getProductId());
            Map<Long, List<ReturnOrder>> returnByProduct = returnOrder.stream()
                    .collect(Collectors.groupingBy(order -> order.getProductId()));

            returnByProduct.forEach((productId, orderList) -> {
                for (StatisticsDTO statistics : statisticsDTOList) {
                    if(statistics.getProductId() == productId){
                        orderList.forEach(orderDetail -> {
                            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(orderDetail.getDateReturn(), java.time.ZoneId.systemDefault());
                            int monthOfDate = zonedDateTime.getMonthValue();
                            if (monthOfDate == monthNumber) {
                                statistics.setSoldQuantity(statistics.getSoldQuantity() - orderDetail.getQuantity());
                            }
                        });
                    }
                }
            });

            for (StatisticsDTO statistic: statisticsDTOList) {
                statistic.setProfit((statistic.getProductActualPrice() - statistic.getProductPriceInput()) * statistic.getSoldQuantity());
            }
        }
        else if(monthNumber != -1 && filterDTO.getProductId() == null) {
            List<OrderDetail> resultOrder = (List<OrderDetail>) orderDetailDao.findAll();
            Map<Long, List<OrderDetail>> groupedByProduct = resultOrder.stream()
                    .collect(Collectors.groupingBy(orderDetail -> orderDetail.getProduct().getId()));

            groupedByProduct.forEach((productId, orderList) -> {
                StatisticsDTO statisticsDTO1 = new StatisticsDTO();
                statisticsDTO1.setSoldQuantity(0);

                statisticsDTO1.setProductId(productId);
                statisticsDTO1.setProductName(productDao.findById(productId).get().getProductName());
                statisticsDTO1.setProductActualPrice(productDao.findById(productId).get().getProductActualPrice());
                statisticsDTO1.setProductPriceInput(productDao.findById(productId).get().getProductPriceInput());
                orderList.forEach(orderDetail -> {
                    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(orderDetail.getTimeSale(), java.time.ZoneId.systemDefault());
                    int monthOfDate = zonedDateTime.getMonthValue();
                    if (monthOfDate == monthNumber) {
                        statisticsDTO1.setSoldQuantity(statisticsDTO1.getSoldQuantity() + orderDetail.getQuantity());
                    }
                });
                statisticsDTOList.add(statisticsDTO1);
            });

            // list return
            List<ReturnOrder> returnOrder = (List<ReturnOrder>) returnOrderDao.findAll();
            Map<Long, List<ReturnOrder>> returnByProduct = returnOrder.stream()
                    .collect(Collectors.groupingBy(order -> order.getProductId()));

            returnByProduct.forEach((productId, orderList) -> {
                for (StatisticsDTO statistics : statisticsDTOList) {
                    if(statistics.getProductId() == productId){
                        orderList.forEach(orderDetail -> {
                            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(orderDetail.getDateReturn(), java.time.ZoneId.systemDefault());
                            int monthOfDate = zonedDateTime.getMonthValue();
                            if (monthOfDate == monthNumber) {
                                statistics.setSoldQuantity(statistics.getSoldQuantity() - orderDetail.getQuantity());
                            }
                        });
                    }
                }
            });

            for (StatisticsDTO statistic: statisticsDTOList) {
                statistic.setProfit((statistic.getProductActualPrice() - statistic.getProductPriceInput()) * statistic.getSoldQuantity());
            }
        } else if (monthNumber == -1 && filterDTO.getProductId() != null) {
            List<OrderDetail> resultOrder = orderDetailDao.findByProductId(filterDTO.getProductId());
            Map<Long, List<OrderDetail>> groupedByProduct = resultOrder.stream()
                    .collect(Collectors.groupingBy(orderDetail -> orderDetail.getProduct().getId()));

            groupedByProduct.forEach((productId, orderList) -> {
                StatisticsDTO statisticsDTO1 = new StatisticsDTO();
                statisticsDTO1.setSoldQuantity(0);

                statisticsDTO1.setProductId(productId);
                statisticsDTO1.setProductName(productDao.findById(productId).get().getProductName());
                statisticsDTO1.setProductActualPrice(productDao.findById(productId).get().getProductActualPrice());
                statisticsDTO1.setProductPriceInput(productDao.findById(productId).get().getProductPriceInput());
                orderList.forEach(orderDetail -> {
                    statisticsDTO1.setSoldQuantity(statisticsDTO1.getSoldQuantity() + orderDetail.getQuantity());
                });
                statisticsDTOList.add(statisticsDTO1);
            });

            // list return
            List<ReturnOrder> returnOrder = returnOrderDao.findByProductId(filterDTO.getProductId());
            Map<Long, List<ReturnOrder>> returnByProduct = returnOrder.stream()
                    .collect(Collectors.groupingBy(order -> order.getProductId()));

            returnByProduct.forEach((productId, orderList) -> {
                for (StatisticsDTO statistics : statisticsDTOList) {
                    if(statistics.getProductId() == productId){
                        orderList.forEach(orderDetail -> {
                            statistics.setSoldQuantity(statistics.getSoldQuantity() - orderDetail.getQuantity());
                        });
                    }
                }
            });

            for (StatisticsDTO statistic: statisticsDTOList) {
                statistic.setProfit((statistic.getProductActualPrice() - statistic.getProductPriceInput()) * statistic.getSoldQuantity());
            }
        } else if (monthNumber == -1 && filterDTO.getProductId() == null) {
            List<OrderDetail> resultOrder = (List<OrderDetail>) orderDetailDao.findAll();
            Map<Long, List<OrderDetail>> groupedByProduct = resultOrder.stream()
                    .collect(Collectors.groupingBy(orderDetail -> orderDetail.getProduct().getId()));

            groupedByProduct.forEach((productId, orderList) -> {
                StatisticsDTO statisticsDTO1 = new StatisticsDTO();
                statisticsDTO1.setSoldQuantity(0);

                statisticsDTO1.setProductId(productId);
                statisticsDTO1.setProductName(productDao.findById(productId).get().getProductName());
                statisticsDTO1.setProductActualPrice(productDao.findById(productId).get().getProductActualPrice());
                statisticsDTO1.setProductPriceInput(productDao.findById(productId).get().getProductPriceInput());
                orderList.forEach(orderDetail -> {
                    statisticsDTO1.setSoldQuantity(statisticsDTO1.getSoldQuantity() + orderDetail.getQuantity());
                });
                statisticsDTOList.add(statisticsDTO1);
            });

            // list return
            List<ReturnOrder> returnOrder = (List<ReturnOrder>) returnOrderDao.findAll();
            Map<Long, List<ReturnOrder>> returnByProduct = returnOrder.stream()
                    .collect(Collectors.groupingBy(order -> order.getProductId()));

            returnByProduct.forEach((productId, orderList) -> {
                for (StatisticsDTO statistics : statisticsDTOList) {
                    if(statistics.getProductId() == productId){
                        orderList.forEach(orderDetail -> {
                            statistics.setSoldQuantity(statistics.getSoldQuantity() - orderDetail.getQuantity());
                        });
                    }
                }
            });

            for (StatisticsDTO statistic: statisticsDTOList) {
                statistic.setProfit((statistic.getProductActualPrice() - statistic.getProductPriceInput()) * statistic.getSoldQuantity());
            }
        }

        return statisticsDTOList ;
    }

    public static Long convertMonthStringToNumber(String monthString) {
        if(monthString.isEmpty() || monthString == null){ return -1L;}
        String lowerCaseMonth = monthString.toLowerCase();
        switch (lowerCaseMonth) {
            case "tháng 1":
                return 1L;
            case "tháng 2":
                return 2L;
            case "tháng 3":
                return 3L;
            case "tháng 4":
                return 4L;
            case "tháng 5":
                return 5L;
            case "tháng 6":
                return 6L;
            case "tháng 7":
                return 7L;
            case "tháng 8":
                return 8L;
            case "tháng 9":
                return 9L;
            case "tháng 10":
                return 10L;
            case "tháng 11":
                return 11L;
            case "tháng 12":
                return 12L;
            default:
                // Trong trường hợp không khớp, trả về một giá trị đặc biệt
                return -1L;
        }
    }
}


