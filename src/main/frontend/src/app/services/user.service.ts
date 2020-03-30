import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';
import { TokenStorageService } from './token-storage.service';

const API_URL = 'http://localhost:8080/api/test/';
const usersUrl = 'http://localhost:8080/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
    private tokenStorage: TokenStorageService) { }

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
  
  findUserByEmail(email: string): Observable<User> {
    let params = new HttpParams();
    params = params.append('email', email);
    
    return this.http.get<User>(usersUrl + 'user', {params: params});
  }

  public getLoggedInUser(): Observable<User> {
    let userToken = this.tokenStorage.getUser();
    return this.findUserByEmail(userToken.username);
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(usersUrl + "all");
  }

  public findStudents(): Observable<User[]> {
    return this.http.get<User[]>(usersUrl + "students");
  }

  public get(id: number): Observable<User> {
    return this.http.get<User>(usersUrl + id);
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

  save(user: User) {
    return this.http.post<User>(usersUrl + "add", user);
  }

  delete(user: User) {
    return this.http.post<User>(usersUrl + "delete", user);
  }
  
  hasRightsToAlter(userId : number) {
    return this.http.post<boolean>(usersUrl + "hasRightsToAlter", userId);
  }


}
