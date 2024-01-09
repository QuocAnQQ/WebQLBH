import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderDetails } from '../_model/order-details.model';
import { MyOrderDetails } from '../_model/order.model';
import { Product } from '../_model/product.model';
import { ProductUpdate } from '../_model/productUpdate';
import { OrderDTO } from '../_model/orderDTO.model';
import { Customer } from '../_model/customer.model';
import { ReturnOrderDetails } from '../_model/returnOrderDetail';
import { User } from '../_model/user.model';
import { FilterDTO } from '../_model/filterDTO.model';
import { StaticDetails } from '../_model/staticDetail.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient) { }

  public createTransaction(amount) {
    return this.httpClient.get("http://localhost:9090/createTransaction/"+amount);
  }

  public markAsDelivered(orderId) {
      return this.httpClient.get("http://localhost:9090/markOrderAsDelivered/"+orderId)
  }

  public getAllOrderDetailsForAdmin(status: string): Observable<MyOrderDetails[]> {
    return this.httpClient.get<MyOrderDetails[]>("http://localhost:9090/getAllOrderDetails/"+status);
  }

  public getMyOrders(): Observable<MyOrderDetails[]> {
    return this.httpClient.get<MyOrderDetails[]>("http://localhost:9090/getOrderDetails");
  }

  public deleteCartItem(cartId) {
    return this.httpClient.delete("http://localhost:9090/deleteCartItem/"+cartId);
  }

  public addProduct(product: FormData) {
    return this.httpClient.post<Product>("http://localhost:9090/addNewProduct", product);
  }

  public getAllProducts(pageNumber, searchKeyword: string = "") {
    return this.httpClient.get<Product[]>("http://localhost:9090/getAllProducts?pageNumber="+pageNumber+"&searchKey="+searchKeyword);
  }

  public getProductDetailsById(productId) {
    return this.httpClient.get<Product>("http://localhost:9090/getProductDetailsById/"+productId);
  }

  public deleteProduct(id: number) {
    return this.httpClient.delete("http://localhost:9090/deleteProductDetails/"+id);
  }

  public getProductDetails(isSingleProductCheckout, productId) {
    return this.httpClient.get<Product[]>("http://localhost:9090/getProductDetails/"+isSingleProductCheckout+"/"+productId);
  }

  public placeOrder(orderDetails: OrderDetails, isCartCheckout) {
    return this.httpClient.post("http://localhost:9090/placeOrder/"+isCartCheckout, orderDetails);
  }

  public addToCart(productId) {
    return this.httpClient.get("http://localhost:9090/addToCart/"+productId);
  }

  public getCartDetails() {
    return this.httpClient.get("http://localhost:9090/getCartDetails");
  }
  public editProduct(productUpdate:any) {
    return this.httpClient.put("http://localhost:9090/editProduct",productUpdate);
  }
  getProductDTOByIdCategory(id: number): Observable<ProductUpdate[]> {
    const url = `http://localhost:9090/getProductByIdCategory/${id}`;
    return this.httpClient.get<ProductUpdate[]>(url, {});
  }
  getAllOrders(): Observable<OrderDTO[]> {
    return this.httpClient.get<OrderDTO[]>(`http://localhost:9090/getOrderDetails`);
  }
  deleteOrderDetail(id: number): Observable<void> {
    return this.httpClient.delete<void>(`http://localhost:9090/deleteOrderDetail/${id}`);
  }

  getAllCustomers(): Observable<Customer[]> {
    return this.httpClient.get<Customer[]>(`http://localhost:9090/api/customers`);
  }

  getAllReturnOrders(): Observable<ReturnOrderDetails[]> {
    
    return this.httpClient.get<ReturnOrderDetails[]>(`http://localhost:9090/api/return-orders`);
  }
  deleteReturnOrder(returnOrderId: number): Observable<void> {
    const url = `http://localhost:9090/api/return-orders/${returnOrderId}`;
    return this.httpClient.delete<void>(url);
  }

  getAllUsers(): Observable<User[]> {
    const url = `http://localhost:9090/getAllUsers`;
    return this.httpClient.get<User[]>(url);
  }
  deleteUser(id: number): Observable<void> {
    const url = `http://localhost:9090/deleteUser/${id}`;
    return this.httpClient.delete<void>(url);
  }


  getProductStatistics(filterDTO: FilterDTO): Observable<StaticDetails[]> {
  return this.httpClient.post<StaticDetails[]>(
      `http://localhost:9090/getProductStatistics`,
      filterDTO,
      
    );
  }
}
