import { FileHandle } from "./file-handle.model";

export interface Product {
    id: number
    productName: string,
    productQuantity :number,
    productDescription: string,
    productPriceInput: number,
    productActualPrice: number,
    productCategoryId:number;
    productImages: FileHandle[]
}