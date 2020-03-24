import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/User';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-admin-admin-list',
  templateUrl: './admin-admin-list.component.html',
  styleUrls: ['./admin-admin-list.component.sass']
})
export class AdminAdminListComponent implements OnInit {
  admins: User[];
  displayedColumnsAdmins: string[] = ['firstName', 'lastName', 'email', 'delete'];
  adminsDataSource: MatTableDataSource<User>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private userService: UserService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.userService.findAdmins().subscribe(
      data => {
        this.admins = data;
        this.adminsDataSource = new MatTableDataSource(this.admins);
        this.adminsDataSource.sort = this.sort;
      });
  }

  delete(user: User) {
    this.userService.delete(user).subscribe(result => {
      this.ngOnInit();
    })
  }

  self(user: User): Boolean {
    return user.email === this.tokenStorage.getUser().username;
  }

}
