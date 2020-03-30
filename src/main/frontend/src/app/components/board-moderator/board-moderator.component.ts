import { Component, OnInit, ViewChild } from '@angular/core';
import { InternshipService } from 'src/app/services/internship.service';
import { Internship } from 'src/app/models/Internship';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { DeletionDialogComponent } from '../deletion-dialog/deletion-dialog.component';

@Component({
  selector: 'app-board-moderator',
  templateUrl: './board-moderator.component.html',
  styleUrls: ['./board-moderator.component.sass']
})
export class BoardModeratorComponent implements OnInit {
  nonValidatedInternships: Internship[];
  internshipsDataSource: MatTableDataSource<Internship>;

  displayedColumnsInternships: string[] = ['student', 'function', 'conventionReference', 'validate', 'delete'];
  
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;


  constructor(private internshipService: InternshipService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.internshipService.findNonValidated().subscribe(
      data => {
        this.nonValidatedInternships = data;
        this.internshipsDataSource = new MatTableDataSource(this.nonValidatedInternships);
        this.internshipsDataSource.paginator = this.paginator;

      }
    )
  }

  openDialog(internship): void {
    const dialogRef = this.dialog.open(DeletionDialogComponent, {
      width: 'auto',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) this.delete(internship);
    });
  }

  delete(internship: Internship) {
    this.internshipService.delete(internship).subscribe(result => {
      this.ngOnInit();
    });
  }

  validateInternship(internship) {
    this.internshipService.validate(internship).subscribe(result => {
      this.ngOnInit();
    });
  }


}
