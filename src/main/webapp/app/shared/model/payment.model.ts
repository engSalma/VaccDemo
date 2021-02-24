import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';

export interface IPayment {
  id?: string;
  method?: PaymentMethod;
}

export class Payment implements IPayment {
  constructor(public id?: string, public method?: PaymentMethod) {}
}
