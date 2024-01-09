import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Category } from 'src/app/_model/category.model';
import { OrderDTO } from 'src/app/_model/orderDTO.model';
import { Product } from 'src/app/_model/product.model';
import { ProductUpdate } from 'src/app/_model/productUpdate';
import { CategoryService } from 'src/app/_service/category.service';
import { ProductService } from 'src/app/_services/product.service';

@Component({
  selector: 'app-create-home',
  templateUrl: './create-home.component.html',
  styleUrls: ['./create-home.component.css']
})
export class CreateHomeComponent implements OnInit {
  orderForm: FormGroup;
  productCategories:Category[];
  selectedProductCategoryId: number;
  selectedProductCategoryName:string;
  products:ProductUpdate[];
  selectedProductId: number;
  orderDTORequest: OrderDTO= {
    id: 0,
    quantity: 0,
    price: 0,
    totalPrice: 0,
    productId: 0,
   customerPhone: '',
    customerName: '',
    categoryId: 0
  };
  

  constructor(private fb: FormBuilder, private matDialog: MatDialog,
    private dialogRef: MatDialogRef<CreateHomeComponent>,
    private categoryService:CategoryService,
    
    private productService:ProductService,
    private toastr: ToastrService,) {
    this.orderForm = this.fb.group({
      productCategory:[''],
      productName: [''],
      quantity: [1],
      price: [0 ],
      totalPrice: [0, ],
      customerName: [''],
      customerPhone: [''] 
    });
   }

  ngOnInit(): void {
    this.getAllProductCategories();

    this.orderForm.get('productName').valueChanges.subscribe((productName) => {
      console.log('productName changed:', productName);
    });
    this.orderForm.get('quantity').valueChanges.subscribe(newValue => {
      this.orderForm.get('totalPrice').setValue(this.orderForm.get('quantity').value * this.orderForm.get('price').value);

    });

  }

  getAllProductCategories() {
    this.categoryService.getAllProductCategories().subscribe(categories => {
      console.log("the categỏỷ", categories)
      this.productCategories = categories;
    
    });
  }

  addNewOrder() {
    this.orderDTORequest.id=0;
   this.orderDTORequest.categoryId=  this.selectedProductCategoryId;
   this.orderDTORequest.productId= this.orderForm.get("productName").value;
   this.orderDTORequest.quantity= this.orderForm.get("quantity").value;
   this.orderDTORequest.price=this.orderForm.get("price").value;
   this.orderDTORequest.totalPrice=this.orderForm.get("totalPrice").value;
   this.orderDTORequest.customerPhone=this.orderForm.get("customerPhone").value;
   this.orderDTORequest.customerName=this.orderForm.get("customerName").value;
   console.log("This.orderRequest",this.orderDTORequest )
    this.categoryService.placeOrder(this.orderDTORequest)
    .subscribe(response => {
      // Xử lý response từ backend ở đây nếu cần
      console.log('Order placed successfully:', response);
      this.toastr.success("Add new order successfully");
      this.matDialog.closeAll();

    }, error => {
      // Xử lý lỗi ở đây nếu có
      console.error('Error placing order:', error);
      this.toastr.error(error);
    });

  }

  closeDialog() {
    this.matDialog.closeAll();
  }
  onProductCategoryChange() {
    // Hàm này sẽ được gọi khi giá trị của selectedProductCategoryId thay đổi
    console.log('Selected category id:', this.selectedProductCategoryId);
    this.getProductByIdcategory(this.selectedProductCategoryId);
    }
  getProductByIdcategory(id:number){
    this.productService.getProductDTOByIdCategory(id).subscribe(
      (data: ProductUpdate[]) => {
        this.products = data;
      },
      (error) => {
        console.error('Error fetching products:', error);
      }
    );
  }
  onProductNameChange(){
console.log("selectedProductId",this.selectedProductId)
this.getPriceByIdProduct(this.selectedProductId);
}
  getPriceByIdProduct( id:number){
    console.log("this.product", this.products)
      // Tìm sản phẩm trong danh sách theo id
  const selectedProduct = this.products.find(product => product.id ===Number(id));

  if (selectedProduct) {
    // Nếu sản phẩm được tìm thấy, thực hiện các bước cần thiết
    console.log('Selected Product:', selectedProduct);
    // Thực hiện các bước khác cần thiết, ví dụ: lấy giá của sản phẩm
    const price = selectedProduct.productActualPrice;
    console.log('Price:', price);
    this.orderForm.get('price').setValue(price);
    this.orderForm.get('totalPrice').setValue(this.orderForm.get('quantity').value * this.orderForm.get('price').value);

  } else {
    console.log('Product not found');
    // Xử lý trường hợp sản phẩm không được tìm thấy
  }
  }

}
