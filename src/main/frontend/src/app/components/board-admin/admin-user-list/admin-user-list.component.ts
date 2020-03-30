import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/User';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-admin-user-list',
  templateUrl: './admin-user-list.component.html',
  styleUrls: ['./admin-user-list.component.sass']
})
export class AdminUserListComponent implements OnInit {
  students: User[];
  p = 1;
  displayedColumnsUsers: string[] = ['Prénom', 'Nom', 'Adresse électronique', 'Profil', 'Supprimer'];

  constructor(private userService: UserService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.userService.findStudents().subscribe(
      data => {
        this.students = data;
      });
  }

  delete(user: User) {
    this.userService.delete(user).subscribe(result => {
      this.ngOnInit();
    })
  }

}
