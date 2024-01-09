import { Component, OnInit } from '@angular/core';
import { Category } from '../_model/category.model';
import { CategoryService } from '../_service/category.service';
import { MatDialog } from '@angular/material/dialog';
import { AddCategoryComponent } from './add-category/add-category.component';
import { DeleteCategoryComponent } from './delete-category/delete-category.component';

@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css']
})
export class ProductCategoryComponent implements OnInit {
   categories:Category[];
   isEditMode = false;

  constructor( private categoryService: CategoryService, private matDialog: MatDialog) { }

  ngOnInit(): void {
    this.getAllProductCategories();

  }
  getAllProductCategories() {
    this.categoryService.getAllProductCategories().subscribe(categories => {
      this.categories = categories;
    });
  }
  
  // Hàm để mở dialog ở chế độ "Sửa"
  openEditForm(category : Category) {

    const dialogRef = this.matDialog.open(AddCategoryComponent, {
      width: "500px",
      data: {
        isEditMode: true,
        category: category
        //truyền hàm category cho add category con để xíu load lại dữ liệu khi thêm
      },
    });
    dialogRef.afterClosed().subscribe(result => {

      console.log('The dialog was closed');
      this.getAllProductCategories();
      if (result !== undefined) {

    }
  });
}

  // Hàm để mở dialog ở chế độ "Thêm mới"
  openAddForm() {
    const dialogRef = this.matDialog.open(AddCategoryComponent, {
      width: "500px",
      data: { isEditMode: false },
    });
    dialogRef.afterClosed().subscribe(result => {

      if(result!== undefined){
        this.categories.push(result);
        this.getAllProductCategories();
      }
    });
  }
  openDeleteCategory(id: number){
    const dialogRef = this.matDialog.open(DeleteCategoryComponent, {
      width: "500px",
      data: {
        id:id


        //truyền hàm category cho add category con để xíu load lại dữ liệu khi thêm
      },
    });
    dialogRef.afterClosed().subscribe(result => {

      if(result!== undefined){

        this.getAllProductCategories();
      }
    });
  }


}
