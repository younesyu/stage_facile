import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Industry } from '../models/Industry';

@Injectable({
  providedIn: 'root'
})
export class IndustryService {
  private industriesUrl: string;

  constructor(private http: HttpClient) {
    this.industriesUrl = "http://localhost:8080/industries/";
  }

  public findAll(): Observable<Industry[]> {
    return this.http.get<Industry[]>(this.industriesUrl + "all");
  }
}
