import { OrderQuantity } from "./order-quantity.model";

export interface OrderDetails {
    id: number;
    productName: string;
    quantity:number;
    price: number;
    totalPrice: number;
    timeSale: string,
    
   
}