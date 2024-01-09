import { HttpClient } from '@angular/common/http';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Product } from 'src/app/_model/product.model';
import { ProductUpdate } from 'src/app/_model/productUpdate';
import { CategoryService } from 'src/app/_service/category.service';
import { ProductService } from 'src/app/_services/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {
  @Input() selectedProduct: any ;
  ProductInfo : Product;
  ProductForm: any;
  productUpdate:ProductUpdate=new ProductUpdate();

  constructor( private formBuilder: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private productService:ProductService,
    private matDialog: MatDialog,
    private dialogRef: MatDialogRef<EditProductComponent>,
    private toastr: ToastrService,
    private http: HttpClient) { 
      this.selectedProduct = data.product;
      this.ProductForm = this.formBuilder.group({
        productName: new FormControl(this.selectedProduct.productName),
        productDescription: new FormControl(this.selectedProduct.productDescription ),
        productQuantity: new FormControl(this.selectedProduct.productQuantity ), 
        productActualPrice: new FormControl(this.selectedProduct.productActualPrice ), // Pattern to allow decimal numbers with up to 2 decimal places
      });
    }

  ngOnInit(): void {
    console.log("the select product", this.selectedProduct);
    this.ProductForm.valueChanges.subscribe((value) => {
      console.log("value", value);
       this.productUpdate.id= this.selectedProduct.id;
       this.productUpdate.productName=value.productName;
       this.productUpdate.productQuantity=value.productQuantity;
       this.productUpdate.productDescription=value.productDescription;
       this.productUpdate.productActualPrice=value.productActualPrice;
    });
  }
  closeDialog() {
    this.matDialog.closeAll();
  }


  editProduct() {
    console.log(this.productUpdate)
    this.productService.editProduct( this.productUpdate).subscribe( data =>{
    this.dialogRef.close(this.ProductInfo);
    this.toastr.success("Sửa thông tin thành công");
     }
    , error =>
    {
      this.toastr.error("Sửa thông tin thất bại");
    });
  }
}
