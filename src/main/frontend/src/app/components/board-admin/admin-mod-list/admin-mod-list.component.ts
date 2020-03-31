import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/User';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { DeletionDialogComponent } from '../../deletion-dialog/deletion-dialog.component';

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

  constructor(private userService: UserService,
    public dialog: MatDialog) { }

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

  openDialog(user): void {
    const dialogRef = this.dialog.open(DeletionDialogComponent, {
      width: 'auto',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) this.delete(user);
    });
  }

  delete(user: User) {
    this.userService.delete(user).subscribe(result => {
      this.ngOnInit();
    })
  }
}
