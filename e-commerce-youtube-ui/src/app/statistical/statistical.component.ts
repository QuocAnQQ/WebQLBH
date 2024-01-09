import { Component, OnInit } from '@angular/core';
import { StaticDetails } from '../_model/staticDetail.model';
import { ProductService } from '../_services/product.service';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Product } from '../_model/product.model';
import { HttpErrorResponse } from '@angular/common/http';
import { FilterDTO } from '../_model/filterDTO.model';

@Component({
  selector: 'app-statistical',
  templateUrl: './statistical.component.html',
  styleUrls: ['./statistical.component.css']
})
export class StatisticalComponent implements OnInit {
  index:number=1;
  pageNumber: number = 0;
  productList:Product[];
  filterDTO:FilterDTO={
    productId:0,
    month:''
  }
  productDetails: Product[]=[];
  selectMonth: string; // Declare selectMonth property
  selectedProductId: number; // Declare selectedProductId property
  displayedColumns: string[] = ['Index','productName', 'productPriceInput', 'productActualPrice', 'soldQuantity', 'profit'];
  staticDetails:StaticDetails[]=[];
  months: string[] = [
    "Tháng 1",
    "Tháng 2",
    "Tháng 3",
    "Tháng 4",
    "Tháng 5",
    "Tháng 6",
    "Tháng 7",
    "Tháng 8",
    "Tháng 9",
    "Tháng 10",
    "Tháng 11",
    "Tháng 12"
  ];

  constructor(private productService: ProductService,
 
    private matdialog: MatDialog,
    
    private toastr:ToastrService,) { }

  ngOnInit(): void {
    this.getAllProducts();
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
  fetchProductStatistics() {
    

    this.productService
      .getProductStatistics(this.filterDTO)
      .subscribe((data) => {
        this.staticDetails = data;
      });
  }

  updateFilterDTO() {
    this.filterDTO.month = this.selectMonth||null;
    this.filterDTO.productId = this.selectedProductId||null;
    // You can log or perform other actions when the values change
    console.log('Filter DTO updated:', this.filterDTO);
    this.fetchProductStatistics();
  }

  resetIndex() {
    this.index = 1;
  }

}
