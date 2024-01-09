import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../_model/category.model';
import { OrderDTO } from '../_model/orderDTO.model';
import { ReturnOrderDetails } from '../_model/returnOrderDetail';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private apiUrl = 'http://localhost:9090/api/product-categories';

  constructor(private http: HttpClient) { }

  getAllProductCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.apiUrl);
  }

  getProductCategoryById(productCategoryId: number): Observable<Category> {
    const url = `${this.apiUrl}/${productCategoryId}`;
    return this.http.get<Category>(url);
  }

  createProductCategory(productCategory: Category): Observable<Category> {
    return this.http.post<Category>(this.apiUrl, productCategory);
  }

  updateProductCategory(productCategoryId: number, updatedProductCategory: Category): Observable<Category> {
    const url = `${this.apiUrl}/${productCategoryId}`;
    return this.http.put<Category>(url, updatedProductCategory);
  }

  deleteProductCategory(productCategoryId: number): Observable<void> {
    const url = `${this.apiUrl}/${productCategoryId}`;
    return this.http.delete<void>(url);
  }
  placeOrder(orderDTO: OrderDTO): Observable<OrderDTO> {
    return this.http.post<OrderDTO>(`http://localhost:9090/addOrderDetail`, orderDTO);
  }
  createReturnOrder(returnOrderDTO: ReturnOrderDetails): Observable<ReturnOrderDetails> {
 
    return this.http.post<ReturnOrderDetails>('http://localhost:9090/api/return-orders', returnOrderDTO);
  }
}
