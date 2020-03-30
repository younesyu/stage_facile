import { Component, OnInit, ViewChild } from '@angular/core';
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
  industryCounts: IndustryCount[] = [];
  industriesCountsToShow: IndustryCount[] = [];
  displayedColumnsIndustries: string[] = ['name', 'count'];
  p = 1

  constructor(private industryService: IndustryService) {
  }

  ngOnInit(): void {
    this.industryService.getCountsByIndustry().subscribe(data => {
      this.industryCounts = data.map(element => ({ name: element[0], count: element[1] }));

      this.industriesCountsToShow = this.industryCounts;
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
