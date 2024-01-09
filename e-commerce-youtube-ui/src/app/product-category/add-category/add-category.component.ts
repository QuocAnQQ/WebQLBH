import { HttpClient } from '@angular/common/http';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Category } from 'src/app/_model/category.model';
import { CategoryService } from 'src/app/_service/category.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent implements OnInit {

  categoryForm: FormGroup;
  categoryData: Category ;

  @Input() selectedCategory: Category ;
  @Input() isEditMode: boolean;
   categoryInfo : Category;
   isUsernameUnique = true;


  constructor(
    private formBuilder: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private categoryService: CategoryService,
    private matDialog: MatDialog,
    private dialogRef: MatDialogRef<AddCategoryComponent>,
    private toastr: ToastrService,
    private http: HttpClient
  ) {
    this.isEditMode = data.isEditMode;
    this.selectedCategory = data.category;
    this.categoryForm = this.formBuilder.group({
      id:new FormControl(this.selectedCategory?.id || ''),
      productCategoryCode: new FormControl(this.selectedCategory?.productCategoryCode || '',[Validators.required]),// Giá trị ban đầu cho từng trường
      productCategoryName: new FormControl(this.selectedCategory?.productCategoryName || '',[Validators.required]),
  });

  }


  ngOnInit(): void {

    this.categoryForm.valueChanges.subscribe((value) => {
      this.categoryInfo = value;
    });
  }


  addNewCategory() {
    console.log("This category info", this.categoryInfo)
    this.categoryService.createProductCategory(this.categoryInfo).subscribe(
      (res) => {
        console.log(res)
        this.dialogRef.close(this.categoryInfo);
        this.showSuccess("Thêm nhân viên thành công")
      },
      (error) => {
        console.log(error)
        console.log(error.error)
        this.showError(error.error.message)
      }
    );
  }

  closeDialog() {
    this.matDialog.closeAll();
  }


  editCategory() {
    console.log(this.categoryInfo)
    this.categoryService.updateProductCategory(this.categoryInfo.id, this.categoryInfo).subscribe( data =>{
    this.dialogRef.close(this.categoryInfo);
    this.showSuccessEdit();
    

    }
    , error =>
    {
      this.showError(error.error.message);
    });
  }

  showSuccess(message: string) {
    this.toastr.success(message);
  }
  showSuccessEdit() {
    this.toastr.success("Edit product category successfully");
  }

  showError(message: string){
    this.toastr.error(message)
  }



}
