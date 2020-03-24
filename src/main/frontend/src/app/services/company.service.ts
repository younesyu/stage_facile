import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from '../models/Company';
import { Internship } from '../models/Internship';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private companiesUrl: string;

  constructor(private http: HttpClient) {
    this.companiesUrl = "http://localhost:8080/companies/";
  }

  public findAll(): Observable<Company[]> {
    return this.http.get<Company[]>(this.companiesUrl + "all");
  }

  public get(id: number): Observable<Company> {
    return this.http.get<Company>(this.companiesUrl + id);
  }

  public save(company: Company) { 
    return this.http.post<Company>(this.companiesUrl + "add", company);
  }

  public delete(company: Company) {
    return this.http.post<Company>(this.companiesUrl + "delete", company);
  }

  public getInternships(id: number): Observable<Internship[]> {
    return this.http.get<Internship[]>(this.companiesUrl + "internships/" + id);
  } 
}
