import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/User';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { MatDialog } from '@angular/material/dialog';
import { DeletionDialogComponent } from '../../deletion-dialog/deletion-dialog.component';

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
    private tokenStorage: TokenStorageService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.userService.findStudents().subscribe(
      data => {
        this.students = data;
      });
  }

  openDialog(student): void {
    const dialogRef = this.dialog.open(DeletionDialogComponent, {
      width: 'auto',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) this.delete(student);
    });
  }

  delete(user: User) {
    this.userService.delete(user).subscribe(result => {
      this.ngOnInit();
    })
  }

}
