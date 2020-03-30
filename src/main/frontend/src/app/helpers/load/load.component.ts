import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/models/User';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-load',
  templateUrl: './load.component.html',
  styleUrls: ['./load.component.sass']
})
export class LoadComponent implements OnInit {
  apiUrl = 'http://localhost:8080/'

  constructor(private http: HttpClient, 
    private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.http.get<any>(this.apiUrl + "api/test/omg").subscribe(_ => {
      this.http.get<any>(this.apiUrl + "internships/load").subscribe(_ => {
        let admin = {
          'firstName': 'admin',
          'lastName': 'admin',
          'username': 'admin@admin.com',
          'password': 'admin',
          'gender': true,
          'role': 'admin',
        }
        this.authService.register(admin).subscribe(_ => {
          setTimeout(_ => {
          console.log(4)
          this.router.navigate(['/']);
          }, 5000)
        }, err => {
          console.log(1)
          this.router.navigate(['/']);
        });
      }, err => {
        console.log(2)
        this.router.navigate(['/']);
      });
    }, err => {
      this.router.navigate(['/']);
    });
  }

}
