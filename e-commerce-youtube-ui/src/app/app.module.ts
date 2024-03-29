import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AuthGuard } from './_auth/auth.guard';
import { AuthInterceptor } from './_auth/auth.interceptor';
import { UserService } from './_services/user.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AddNewProductComponent } from './add-new-product/add-new-product.component';
import {MatGridListModule} from '@angular/material/grid-list';
import { DragDirective } from './drag.directive';
import { ShowProductDetailsComponent } from './show-product-details/show-product-details.component';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import { ShowProductImagesDialogComponent } from './show-product-images-dialog/show-product-images-dialog.component';
import {MatDialogModule} from '@angular/material/dialog';
import { ProductViewDetailsComponent } from './product-view-details/product-view-details.component';
import { BuyProductComponent } from './buy-product/buy-product.component';
import { OrderConfirmationComponent } from './order-confirmation/order-confirmation.component';
import { RegisterComponent } from './register/register.component';
import { CartComponent } from './cart/cart.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { ProductCategoryComponent } from './product-category/product-category.component';
import { AddCategoryComponent } from './product-category/add-category/add-category.component';
import { DeleteCategoryComponent } from './product-category/delete-category/delete-category.component';
import { ToastrModule } from 'ngx-toastr';
import { NgSelectModule } from '@ng-select/ng-select';
import { MatSelectModule } from '@angular/material/select';
import { EditProductComponent } from './show-product-details/edit-product/edit-product.component';
import { CreateHomeComponent } from './home/create-home/create-home.component';
import { ReturnOrderComponent } from './return-order/return-order.component';
import { AddReturnOrderComponent } from './return-order/add-return-order/add-return-order.component';
import { ManageAccountComponent } from './manage-account/manage-account.component';
import { StatisticalComponent } from './statistical/statistical.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AdminComponent,
    UserComponent,
    LoginComponent,
    HeaderComponent,
    ForbiddenComponent,
    AddNewProductComponent,
    DragDirective,
    ShowProductDetailsComponent,
    ShowProductImagesDialogComponent,
    ProductViewDetailsComponent,
    BuyProductComponent,
    OrderConfirmationComponent,
    RegisterComponent,
    CartComponent,
    MyOrdersComponent,
    OrderDetailsComponent,
    ProductCategoryComponent,
    AddCategoryComponent,
    DeleteCategoryComponent,
    ProductViewDetailsComponent,
    EditProductComponent,
    CreateHomeComponent,
    ReturnOrderComponent,
    AddReturnOrderComponent,
    AddReturnOrderComponent,
    ManageAccountComponent,
    StatisticalComponent
  
  
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatGridListModule,
    MatTableModule,
    MatIconModule,
    MatDialogModule,
    ToastrModule.forRoot({
      timeOut: 2000,
      positionClass: 'toast-bottom-right',
      progressBar: true,
      }
    ),
    MatButtonToggleModule, 
    ReactiveFormsModule,
    NgSelectModule,
    MatInputModule,
    MatFormFieldModule ,
    MatSelectModule
  ],
  providers: [
    AuthGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass:AuthInterceptor,
      multi:true
    },
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
