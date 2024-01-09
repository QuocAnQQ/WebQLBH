import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { ImageProcessingService } from '../image-processing.service';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';
import { MatDialog } from '@angular/material/dialog';
import { EditProductComponent } from '../show-product-details/edit-product/edit-product.component';
import { OrderDetails } from '../_model/order-details.model';
import { CreateHomeComponent } from './create-home/create-home.component';
import { OrderDTO } from '../_model/orderDTO.model';
import { Toast, ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
   orderDetail:OrderDetails[];

  showLoadMoreProductButton = false;
  showTable = false;
  pageNumber: number = 0;
  orderDetails: OrderDTO[];
  index:number=1;
  productDetails: Product[] = [];
  displayedColumns: string[] = ['Index', 'productName','Price', 'Product Quantity', 'Total Price', 'customerName', 'Actions'];


  constructor(private productService: ProductService,
 
    private matdialog: MatDialog,
    private imageProcessingService: ImageProcessingService,
    private toastr:ToastrService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAllOrderDetail();
   
  }
  openAddForm(){
    const dialogRef = this.matdialog.open(CreateHomeComponent, {
      width: "700px",
      data: { },
    });
    dialogRef.afterClosed().subscribe(result => {

      if(result!== undefined){
        this.productDetails.push(result);
        this.getAllOrderDetail();
        // this.getAllProductCategories();
      }
    });
  
  }
  getAllOrderDetail(){
    this.productService.getAllOrders().subscribe(
      (data: OrderDTO[]) => {
        this.orderDetails = data;
      },
      error => {
        console.error('Error fetching orders', error);
      }
    );
  }

  deleteProduct(id: number){
    console.log("id", id)
    this.productService.deleteOrderDetail(id).subscribe(
      () => {
        console.log('Order deleted successfully');
        this.getAllOrderDetail();
        this.toastr.success("Delete order successfully")
        
       
      },
      error => {
        console.error('Error deleting order', error);
        this.toastr.error(error);
        
      }
    );

  }

  


  resetIndex() {
    this.index = 1;
  }
}
