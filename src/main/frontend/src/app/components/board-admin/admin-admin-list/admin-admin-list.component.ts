import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/User';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/services/user.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { MatDialog } from '@angular/material/dialog';
import { DeletionDialogComponent } from '../../deletion-dialog/deletion-dialog.component';

@Component({
  selector: 'app-admin-admin-list',
  templateUrl: './admin-admin-list.component.html',
  styleUrls: ['./admin-admin-list.component.sass']
})
export class AdminAdminListComponent implements OnInit {
  admins: User[];
  displayedColumnsAdmins: string[] = ['Prénom', 'Nom', 'Email', 'Profil', 'Supprimer'];

  constructor(private userService: UserService,
    private tokenStorage: TokenStorageService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.userService.findAdmins().subscribe(data => {
        this.admins = data;
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

  self(user: User): Boolean {
    return user.email === this.tokenStorage.getUser().username;
  }

}
