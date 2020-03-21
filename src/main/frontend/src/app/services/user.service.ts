import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';

const API_URL = 'http://localhost:8080/api/test/';
const usersUrl = 'http://localhost:8080/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(usersUrl + "all");
  }

  public findStudents(): Observable<User[]> {
    return this.http.get<User[]>(usersUrl + "students");
  }

  public findModerators(): Observable<User[]> {
    return this.http.get<User[]>(usersUrl + "moderators");
  }

  public findAdmins(): Observable<User[]> {
    return this.http.get<User[]>(usersUrl + "admins");
  }

  getNonValidatedMods(): Observable<User[]> {
    return this.http.get<User[]>(usersUrl + 'nonValidatedMods');
  }

  validateMod(moderator: User) {
    return this.http.post<User>(usersUrl + 'validate', moderator);
  }

  delete(user: User) {
    return this.http.post<User>(usersUrl + "delete", user);
  }
}
