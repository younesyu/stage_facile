import { Component, OnInit, ViewChild } from '@angular/core';
import { InternshipService } from 'src/app/services/internship.service';
import { Industry } from 'src/app/models/Industry';
import { MatTableDataSource, MatTable, MatTableModule } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { IndustryService } from 'src/app/services/industry.service';

export interface IndustryCount {
  name: string;
  count: number;
}

@Component({
  selector: 'app-industry-stats',
  templateUrl: './industry-stats.component.html',
  styleUrls: ['./industry-stats.component.sass']
})
export class IndustryStatsComponent implements OnInit {
  industries: Industry[];
  industryCounts: IndustryCount[];
  industriesCountsToShow: IndustryCount[];
  displayedColumnsIndustries: string[] = ['name', 'count'];

  @ViewChild("table")
  table: MatTable<IndustryCount>;

  @ViewChild("paginator")
  paginator: MatPaginator;

  constructor(private industryService: IndustryService) {
    this.industryCounts = [];
  }

  ngOnInit(): void {
    // this.industryService.findAll().subscribe(data => {
    //   // Store all the retrieved industries
    //   this.industries = data;

    //   // Count every industry's number of occurences
    //   // in all the retrieved internships
    //   let industryMap = new Map<string, number>();
    //   this.internshipService.findAll().subscribe(data => {
    //     data.forEach(internship => {
    //       let industryName = internship.industry.name;
    //       if (!industryMap.get(industryName)) {
    //         industryMap.set(industryName, 0);
    //       }
    //       let newVal = industryMap.get(industryName) + 1;
    //       industryMap.set(industryName, newVal);
    //     });

    //     // Fill the list of all counted industries with
    //     // number of occurrences
    //     let countedIndustries = Array.from(industryMap.keys());
    //     industryMap.forEach((count, name) => {
    //       let currentIndustry = { name: name, count: count };
    //       this.industryCounts.push(currentIndustry);
    //     });

    //     // Get remaining industries by removing all counted
    //     // industries from the list of all industries
    //     let remainingIndustries =
    //       this.industries.filter(industry =>
    //         !countedIndustries.includes(industry.name));

    //     // Fill the list of all remaining industries with
    //     // number of occurrences set to 0
    //     remainingIndustries.forEach(remainingIndustry => {
    //       let remainingIndustryCount = { name: remainingIndustry.name, count: 0 };
    //       this.industryCounts.push(remainingIndustryCount);
    //     });

    //     // Set up data source
    //     this.industriesCountsToShow = this.industryCounts;
    //     let dataSource = new MatTableDataSource<IndustryCount>(this.industriesCountsToShow);
    //     dataSource.paginator = this.paginator;
    //     this.table.dataSource = dataSource;
    //     this.table.renderRows();

    //   });
    // });

    this.industryService.getCountsByIndustry().subscribe(data => {
      this.industryCounts = data.map(element => ({ name: element[0], count: element[1] }));

      this.industriesCountsToShow = this.industryCounts;
      this.table.dataSource = this.industriesCountsToShow;
      this.table.renderRows();
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    let filteredIndustries =
      this.industryCounts
        .map(i => i.name)
        .filter(c => c.toLowerCase().includes(filterValue));

    this.industriesCountsToShow = this.industryCounts.filter(i =>
      filteredIndustries.includes(i.name));
  }

}


// let industryMap = new Map<string, number>();
    // let industryCounter: IndustryCount[] = [];

    // this.industryService.findAll().subscribe(data => {
    //   this.industries = data;
    //   data.forEach(industry => {
    //     industryMap.set(industry.name, 0);
    //   });

    //   this.internshipService.findAll().subscribe(data => {
    //     data.forEach(internship => {
    //         let newVal = industryMap.get(internship.industry.name) + 1;
    //         industryMap.set(internship.industry.name, newVal);
    //     });

    //     industryMap.forEach((count, name) => {
    //       industryCounter.push({ name: name, count: count});
    //     });
    //     this.industriesDataSource = new MatTableDataSource(industryCounter);
    //     this.industriesDataSource.paginator = this.paginator;
    //     this.industriesDataSource.sort = this.sort;
    //   });
    // });
