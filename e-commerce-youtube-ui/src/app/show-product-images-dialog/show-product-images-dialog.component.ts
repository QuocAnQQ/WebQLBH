import { Component, Inject, OnInit } from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { FileHandle } from '../_model/file-handle.model';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-show-product-images-dialog',
  templateUrl: './show-product-images-dialog.component.html',
  styleUrls: ['./show-product-images-dialog.component.css']
})
export class ShowProductImagesDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.receiveImages();
  }

  receiveImages() {
    console.log(this.data);
 
  }
  getSafeURL(imagePath: string): any {
    const url = ` http://192.168.1.2:8080/${imagePath}`; // Chỉ định URL của HTTP server
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }
 

}
