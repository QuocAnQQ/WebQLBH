import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AddReturnOrderComponent } from './add-return-order/add-return-order.component';
import { ReturnOrderDetails } from '../_model/returnOrderDetail';

@Component({
  selector: 'app-return-order',
  templateUrl: './return-order.component.html',
  styleUrls: ['./return-order.component.css']
})
export class ReturnOrderComponent implements OnInit {
  returnOrderDetails: ReturnOrderDetails[];
  displayedColumns: string[] = ['Index', 'productName', 'Product Quantity', 'reasonReturn', 'dateReturn','customerName' ];
  index:number=1;
  constructor(private productService: ProductService,
 
    private matdialog: MatDialog,
    
    private toastr:ToastrService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAllOrderDetail();
  }
  openAddForm(){
    const dialogRef = this.matdialog.open(AddReturnOrderComponent, {
      width: "700px",
      data: { },
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getAllOrderDetail();

      if(result!== undefined){
        this.returnOrderDetails.push(result);
        this.getAllOrderDetail();
        // this.getAllProductCategories();
      }
    });
  
  }
  getAllOrderDetail(){
    this.productService.getAllReturnOrders().subscribe(
      (returnOrders) => {
        this.returnOrderDetails = returnOrders;
        console.log('Return orders:', this.returnOrderDetails);
      },
      (error) => {
        console.error('Error loading return orders:', error);
      }
    );
  
  }

  deleteProduct(id: number){
    console.log("id", id)
    this.productService.deleteReturnOrder(id).subscribe(
      () => {
        console.log('Return order deleted successfully.');
        this.toastr.success("Delete return order successfully")
        this.getAllOrderDetail();
        // Thực hiện các xử lý khác sau khi xóa đơn hàng trả lại
      },
      (error) => {
        console.error('Error deleting return order:', error);
        this.toastr.error(error);
      }
    );

}
resetIndex() {
  this.index = 1;
}
}
