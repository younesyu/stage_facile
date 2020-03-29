import { Internship } from './Internship';

export class User {
    id: number;
    password:string;
    email: string;
    firstName: string;
    lastName: string;
    birthDate: Date;
    gender: boolean;
    role: { "id": number, "name": string };
    internships: Internship[];
}