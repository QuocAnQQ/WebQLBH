import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Customer } from 'src/app/_model/customer.model';
import { Product } from 'src/app/_model/product.model';
import { ReturnOrderDetails } from 'src/app/_model/returnOrderDetail';
import { CategoryService } from 'src/app/_service/category.service';
import { ProductService } from 'src/app/_services/product.service';

@Component({
  selector: 'app-add-return-order',
  templateUrl: './add-return-order.component.html',
  styleUrls: ['./add-return-order.component.css']
})
export class AddReturnOrderComponent implements OnInit {
  pageNumber: number = 0;
  productDetails: any[];
  productList:Product[];
  customers:Customer[];
  returnOrderForm:FormGroup;
  selectedProductId:number;
  selectedCustomerId:number;
  returnOrderDetails: ReturnOrderDetails= {
    productId: 0,
    productName: '',
    quantity: 0,
    reasonReturn: '',
    dateReturn: '',
    customerId: 0,
  };

  constructor(private fb: FormBuilder, private matDialog: MatDialog,

    private dialogRef: MatDialogRef<AddReturnOrderComponent>,
    private categoryService:CategoryService,
    
    private productService:ProductService,
    private toastr: ToastrService) { 

    }

  ngOnInit(): void {
    this.getAllProducts();
    this.getAllCustomer();
    this.returnOrderForm = this.fb.group({
      productName: [null],
      quantity: [0],
      reason: [null],
      customerName: [null],
    });

  }
  public getAllProducts(searchKeyword: string = "") {
    this.productDetails=[];

    console.log(this.pageNumber)
    console.log(searchKeyword)
    this.productService.getAllProducts(this.pageNumber, searchKeyword)
    // .pipe(
    //   map((x: Product[], i) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
    // )
    .subscribe(
      (resp: Product[]) => {
       console.log(resp);
         resp.forEach(product => this.productDetails.push(product));
        console.log('msg', this.productDetails);
        this.productList= resp;

        // this.productDetails = resp;
      }, (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  addNewOrder() {
    this.returnOrderDetails.productId= this.returnOrderForm.get("productName").value;
   this.returnOrderDetails.quantity= this.returnOrderForm.get("quantity").value;

   this.returnOrderDetails.reasonReturn= this.returnOrderForm.get("reason").value;
   this.returnOrderDetails.customerId=this.returnOrderForm.get("customerName").value;

   console.log("This.orderRequest",this.returnOrderDetails )
    this.categoryService.createReturnOrder(this.returnOrderDetails)
    .subscribe(response => {
      // Xử lý response từ backend ở đây nếu cần
      console.log('Order placed successfully:', response);
      
      this.toastr.success("Add new return order successfull")
    }, error => {
      // Xử lý lỗi ở đây nếu có
      console.error('Error placing order:', error);
      this.toastr.error(error);
    });
    this.matDialog.closeAll();
  }



  closeDialog() {
    this.matDialog.closeAll();
  }
 
 getAllCustomer(){
  this.productService.getAllCustomers().subscribe(
    (data: Customer[]) => {
      this.customers = data;
    },
    error => {
      console.error('Error fetching customers', error);
    }
  );
}
onProductNameChange(): void {
  console.log('Selected Product ID:', this.selectedProductId);
  // Add any additional logic you want to perform when the product selection changes
}

onCustomerChange(): void {
  console.log('Selected Customer ID:', this.selectedCustomerId);
  // Add any additional logic you want to perform when the customer selection changes
  }
}
