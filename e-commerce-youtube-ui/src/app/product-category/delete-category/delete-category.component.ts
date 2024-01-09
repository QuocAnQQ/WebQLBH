import { Component, Inject, Input, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from 'src/app/_service/category.service';

@Component({
  selector: 'app-delete-category',
  templateUrl: './delete-category.component.html',
  styleUrls: ['./delete-category.component.css']
})
export class DeleteCategoryComponent implements OnInit {
  @Input() id: number

  constructor(    private matDialog: MatDialog,

    private categoryService: CategoryService,
    @Inject (MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<DeleteCategoryComponent>) { this.id=this.data.id; }

  ngOnInit(): void {
   
  }
  deleteEmployee(id: number){
    console.log(id);
    this.categoryService.deleteProductCategory(id).subscribe( data => {
      this.dialogRef.close(id);
 })
  }
  cancle(){

    this.matDialog.closeAll(); }


}
