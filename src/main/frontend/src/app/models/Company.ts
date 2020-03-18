import { Industry } from "./Industry";

export class Company {
    id: number;
    name: string;
    industry: Industry;
    headOfficeAddress: string;
    postalCode: string;
    city: string;
    country: string;
}