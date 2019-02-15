import { IProductOrder } from 'app/shared/model/product-order.model';

export const enum Gender {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    OTHER = 'OTHER'
}

export interface ICustomer {
    id?: number;
    firstName?: string;
    lastName?: string;
    gender?: Gender;
    email?: string;
    phone?: string;
    addressLine1?: string;
    addressLine2?: string;
    city?: string;
    country?: string;
    userLogin?: string;
    userId?: number;
    orders?: IProductOrder[];
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public gender?: Gender,
        public email?: string,
        public phone?: string,
        public addressLine1?: string,
        public addressLine2?: string,
        public city?: string,
        public country?: string,
        public userLogin?: string,
        public userId?: number,
        public orders?: IProductOrder[]
    ) {}
}
