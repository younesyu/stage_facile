import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Internship } from '../models/Internship';

@Injectable({
  providedIn: 'root'
})
export class InternshipService {
  private internshipsUrl: string;
  
  constructor(private http: HttpClient) {
    this.internshipsUrl = "http://localhost:8080/internships/";
  }

  public findAll(): Observable<Internship[]> {
    return this.http.get<Internship[]>(this.internshipsUrl + "all");
  }

  public get(id: number): Observable<Internship> {
    return this.http.get<Internship>(this.internshipsUrl + id);
  }

  public save(internship: Internship) { 
    return this.http.post<Internship>(this.internshipsUrl + "add", internship);
  }


}
