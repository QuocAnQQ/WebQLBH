import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { ImageProcessingService } from '../image-processing.service';
import { ShowProductImagesDialogComponent } from '../show-product-images-dialog/show-product-images-dialog.component';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';
import { EditProductComponent } from './edit-product/edit-product.component';

@Component({
  selector: 'app-show-product-details',
  templateUrl: './show-product-details.component.html',
  styleUrls: ['./show-product-details.component.css']
})
export class ShowProductDetailsComponent implements OnInit {

  showLoadMoreProductButton = false;
  showTable = false;
  pageNumber: number = 0;
  index:number=1;
  productDetails: Product[] = [];
  displayedColumns: string[] = ['Index', 'Product Name', 'description', 'Product Quantity', 'Product Actual Price', 'Actions'];


  constructor(private productService: ProductService,
    public imagesDialog: MatDialog,
    private matdialog: MatDialog,
    private imageProcessingService: ImageProcessingService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAllProducts();
  }

  searchByKeyword(searchkeyword) {
    console.log(searchkeyword);
    this.pageNumber = 0;
    this.productDetails = [];
    this.getAllProducts(searchkeyword);
  }

  public getAllProducts(searchKeyword: string = "") {
    this.productDetails=[];
    this.showTable = false;
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
        this.showTable = true;

        if(resp.length == 12) {
          this.showLoadMoreProductButton = true;
        } else {
          this.showLoadMoreProductButton = false;
        }

        // this.productDetails = resp;
      }, (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  loadMoreProduct() {
    this.pageNumber = this.pageNumber + 1;
    this.getAllProducts();
  }

  deleteProduct(productId) {
    console.log("the id", productId);
    this.productService.deleteProduct(productId).subscribe(
      (resp) => {
        this.getAllProducts();
      },
      (error:HttpErrorResponse) => {
        console.log(error);
      }
    );
  }

  showImages(product: any) {
    console.log(product);
    this.imagesDialog.open(ShowProductImagesDialogComponent, {
      data: {
        images: product.productImageURL
      },
      height: '500px',
      width: '800px'
    });
  }

  editProductDetails( element: Product) { console.log("The elêmnt", element)

    const dialogRef = this.matdialog.open(EditProductComponent, {
      width: "500px",
      data: {
        product: element
        //truyền hàm category cho add category con để xíu load lại dữ liệu khi thêm
      },
    });
    dialogRef.afterClosed().subscribe(result => {

      console.log('The dialog was closed');
      this.getAllProducts();
      if (result !== undefined) {}
  });
}
  resetIndex() {
    this.index = 1;
  }
}
