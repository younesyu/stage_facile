import { ComponentFactory } from "@angular/core";
import { Company } from "./Company";
import { Industry } from "./Industry";

export class Internship {
    id: number;
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