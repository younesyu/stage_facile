import { Component, OnInit } from '@angular/core';
import { InternshipService } from 'src/app/services/internship.service';

@Component({
  selector: 'app-country-stats',
  templateUrl: './country-stats.component.html',
  styleUrls: ['./country-stats.component.sass']
})
export class CountryStatsComponent implements OnInit {

  public pieChartLabels: string[];
  public pieChartData: number[];
  public pieChartType = 'pie';

  constructor(private internshipService: InternshipService) { }

  ngOnInit(): void {
    let countryMap = new Map<string, number>();

    this.internshipService.findAll().subscribe(data => {
      data.forEach(internship => {
        
        if(countryMap.get(internship.location)) {
          let newVal = countryMap.get(internship.location) + 1;
          countryMap.set(internship.location, newVal);
        } else {
          countryMap.set(internship.location, 1);
        }
      });
      this.pieChartLabels = Array.from(countryMap.keys());
      this.pieChartData = Array.from(countryMap.values());
    });
  }

}
