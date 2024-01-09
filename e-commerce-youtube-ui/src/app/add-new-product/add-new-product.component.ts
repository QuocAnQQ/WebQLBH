import { HttpErrorResponse } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { NgForm, NgModel } from "@angular/forms";
import { DomSanitizer } from "@angular/platform-browser";
import { ActivatedRoute } from "@angular/router";
import { FileHandle } from "../_model/file-handle.model";
import { Product } from "../_model/product.model";
import { ProductService } from "../_services/product.service";
import { Category } from "../_model/category.model";
import { CategoryService } from "../_service/category.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-add-new-product",
  templateUrl: "./add-new-product.component.html",
  styleUrls: ["./add-new-product.component.css"],
})
export class AddNewProductComponent implements OnInit {
  isNewProduct = true;
  selectedCategoryId:number;
  productCategories: Category[];

  product: Product = {
    id: null,
    productName: "",
    productQuantity :0,
    productDescription: "",
    productPriceInput: 0,
    productActualPrice: 0,
    productCategoryId: 0,
    productImages: [],
  };

  constructor(
    private productService: ProductService,
    private sanitizer: DomSanitizer,
    private activatedRoute: ActivatedRoute,
    private categoryService:CategoryService,
    private toastr: ToastrService

  ) {}

  ngOnInit(): void {
    this.getAllProductCategories();
    this.product = this.activatedRoute.snapshot.data['product'];
    console.log(this.product);

    if(this.product && this.product.id) {
      this.isNewProduct = false;
    }
  }
  getAllProductCategories() {
    this.categoryService.getAllProductCategories().subscribe(categories => {
      this.productCategories = categories;
    });
  }
  onCategorySelectionChange() {
    console.log('Selected Category ID:', this.selectedCategoryId);
    this.product.productCategoryId=this.selectedCategoryId;
    
    
  }

  addProduct(productForm: NgForm) {
    const formData = this.prepareFormDataForProduct(this.product);
    console.log("this.formdata", formData)
    this.productService.addProduct(formData).subscribe(
      (response: Product) => {
        productForm.reset();
        this.toastr.success("Add new cloth iteam successfully")
        
        this.product.productImages = [];
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        this.toastr.success("Add new cloth iteam successfully")
      }
    );
  }

  // prepareFormDataForProduct(product: Product): FormData {
  //   const uploadImageData = new FormData();
  //   uploadImageData.append(
  //     "product",
  //     new Blob([JSON.stringify(product)], { type: "application/json" })
  //   );

  //   for (var i = 0; i < product.productImages.length; i++) {
  //     uploadImageData.append(
  //       "imageFile",
  //       this.product.productImages[i].file,
  //       this.product.productImages[i].file.name
  //     );
  //   }
  //   uploadImageData.append("product", JSON.stringify(product));
  //   return uploadImageData;
  // }
  prepareFormDataForProduct(product: Product): FormData {
    const uploadImageData = new FormData();
  
    // Đưa thông tin sản phẩm vào FormData
    // uploadImageData.append("product", JSON.stringify(product));
  
    // Đưa các hình ảnh liên quan vào FormData
    for (let i = 0; i < product.productImages.length; i++) {
      uploadImageData.append("productImages", product.productImages[i].file, product.productImages[i].file.name);
    }
    uploadImageData.append("productCategoryId", this.selectedCategoryId.toString())
    uploadImageData.append("productName", this.product.productName.toString())
    uploadImageData.append("productQuantity", this.product.productQuantity.toString())
    uploadImageData.append("productDescription", this.product.productDescription.toString())
    uploadImageData.append("productPriceInput", this.product.productPriceInput.toString())
    uploadImageData.append("productActualPrice", this.product.productActualPrice.toString())
    console.log(this.selectedCategoryId.toString())
  
    return uploadImageData;
  }
  

  onFileSelected(event: any) {
    if (event.target.files) {
      const file = event.target.files[0];
      const fileHandle: FileHandle = {
        file: file,
        url: this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file)
        ),
      };
      this.product.productImages.push(fileHandle);
    }
  }

  removeImages(i: number) {
    this.product.productImages.splice(i, 1);
  }

  fileDropped(fileHandle: FileHandle) {
    this.product.productImages.push(fileHandle);
  }
}
