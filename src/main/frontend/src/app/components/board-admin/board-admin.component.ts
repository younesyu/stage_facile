import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/User';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.sass']
})
export class BoardAdminComponent implements OnInit {
  students: User[];
  moderators: User[];
  admins: User[];
  nonValidatedMods: User[];
  p = 1;
  displayedColumnsUsers: string[] = ['firstName', 'lastName', 'email', 'delete'];
  displayedColumnsMods: string[] = ['firstName', 'lastName', 'email', 'validate', 'delete'];
  displayedColumnsAdmins: string[] = ['firstName', 'lastName', 'email', 'delete'];

  studentsDataSource: MatTableDataSource<User>;
  moderatorsDataSource: MatTableDataSource<User>;
  adminsDataSource: MatTableDataSource<User>;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private tokenStorage: TokenStorageService) {
  }

  ngOnInit() {
    this.userService.findStudents().subscribe(
      data => {
        this.students = data;
        this.studentsDataSource = new MatTableDataSource(this.students);
        this.studentsDataSource.paginator = this.paginator;
      });

    this.userService.findModerators().subscribe(
      data => {
        this.moderators = data;
        this.moderatorsDataSource = new MatTableDataSource(this.moderators);
        this.moderatorsDataSource.paginator = this.paginator;
      });

    this.userService.findAdmins().subscribe(
      data => {
        this.admins = data;
        this.adminsDataSource = new MatTableDataSource(this.admins);
        this.adminsDataSource.paginator = this.paginator;
        this.adminsDataSource.sort = this.sort;
      });

    this.userService.getNonValidatedMods().subscribe(
      data => {
        this.nonValidatedMods = data;
      }
    )
  }

  validateMod(moderator: User) {
    this.userService.validateMod(moderator).subscribe(result => {
      this.ngOnInit();
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
