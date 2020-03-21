import { ComponentFactory } from "@angular/core";
import { Company } from "./Company";
import { Industry } from "./Industry";
import { User } from './User';

export class Internship {
    id: number;
    user: User;
    beginDate: Date;
    endDate: Date;
    function: string;
    description: string;
    location: string;
    stipend: number;
    conventionReference: number;
    experienceLevel: string;
    managers: string[];
    validated: boolean;
    foundBy: string;
    company: Company;
    industry: Industry;
}