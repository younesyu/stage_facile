import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/User';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-admin-mod-list',
  templateUrl: './admin-mod-list.component.html',
  styleUrls: ['./admin-mod-list.component.sass']
})
export class AdminModListComponent implements OnInit {
  moderators: User[];

  displayedColumnsMods: string[] = ['firstName', 'lastName', 'email', 'validate', 'delete'];
  moderatorsDataSource: MatTableDataSource<User>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.findModerators().subscribe(
      data => {
        this.moderators = data;
        this.moderatorsDataSource = new MatTableDataSource(this.moderators);
        this.moderatorsDataSource.paginator = this.paginator;
      });

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
}
