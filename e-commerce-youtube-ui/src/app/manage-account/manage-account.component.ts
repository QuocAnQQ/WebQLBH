import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { User } from '../_model/user.model';

@Component({
  selector: 'app-manage-account',
  templateUrl: './manage-account.component.html',
  styleUrls: ['./manage-account.component.css']
})
export class ManageAccountComponent implements OnInit {
  displayedColumns: string[] = ['Index', 'userName',  'role','Actions' ];
  index:number=1;
  AccountDetails: User[]=[];
  constructor(private productService: ProductService,
 
    private matdialog: MatDialog,
    
    private toastr:ToastrService,
    private router: Router) { }

  ngOnInit(): void {
    this.getAllOrderDetail();
  }
  getAllOrderDetail(){
    this.productService.getAllUsers().subscribe(
      (users) => {
        this.AccountDetails = users;
        console.log('Users:', this.AccountDetails);
      },
      (error) => {
        console.error('Error loading users:', error);
      }
    );
  
  }

  deleteProduct(id: number){
    console.log("id", id)
    this.productService.deleteUser(id).subscribe(
      () => {
        console.log('User deleted successfully.');
        this.toastr.success("delete user successfully")
        // Gọi hàm load lại danh sách user hoặc thực hiện các bước cần thiết khác
      },
      (error) => {
        console.error('Error deleting user:', error);
        this.toastr.error(error);
      }
    );

}
resetIndex() {
  this.index = 1;
}

}
