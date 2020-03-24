import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/User';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-admin-user-list',
  templateUrl: './admin-user-list.component.html',
  styleUrls: ['./admin-user-list.component.sass']
})
export class AdminUserListComponent implements OnInit {
  students: User[];
  studentsDataSource: MatTableDataSource<User>;


  displayedColumnsUsers: string[] = ['firstName', 'lastName', 'email', 'delete'];
  
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private userService: UserService,
    private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.userService.findStudents().subscribe(
      data => {
        this.students = data;
        this.studentsDataSource = new MatTableDataSource(this.students);
        this.studentsDataSource.paginator = this.paginator;
      });
  }

  delete(user: User) {
    this.userService.delete(user).subscribe(result => {
      this.ngOnInit();
    })
  }

}
