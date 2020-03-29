import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Internship } from '../models/Internship';
import { Review } from '../models/Review';

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

  public findValidated(): Observable<Internship[]> {
    return this.http.get<Internship[]>(this.internshipsUrl + "validated");
  }

  public findNonValidated(): Observable<Internship[]> {
    return this.http.get<Internship[]>(this.internshipsUrl + "nonValidated");
  }

  public validate(internship): Observable<any> {
    return this.http.post<Internship>(this.internshipsUrl + "validate", internship);
  }

  public get(id: number): Observable<Internship> {
    return this.http.get<Internship>(this.internshipsUrl + id);
  }

  public save(internship: Internship) {
    return this.http.post<Internship>(this.internshipsUrl + "add", internship);
  }

  public delete(internship: Internship) {
    return this.http.post<Internship>(this.internshipsUrl + "delete", internship);
  }

  addReview(internshipId: number, reviewObject: any) {
    let params = new HttpParams()
      .set("internshipId", internshipId.toString());

    return this.http.post<any>(this.internshipsUrl + "addReview", reviewObject, {params})
  }

  getReview(internshipId: number) {
    return this.http.get<Review>(this.internshipsUrl + internshipId + "/review");
  }


  getUserInternships(id: number): Observable<Internship[]> {
    return this.http.get<Internship[]>("http://localhost:8080/users/" + id + "/internships");
  }

  countByGender(): Observable<any> {
    return this.http.get<any>(this.internshipsUrl + "countByGender");
  }

  countByYear(): Observable<Map<number, number>> {
    return this.http.get<Map<number, number>>(this.internshipsUrl + "countByYear");
  }

}
