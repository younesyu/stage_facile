import { Component, OnInit } from '@angular/core';
import { InternshipService } from 'src/app/services/internship.service';

@Component({
  selector: 'app-year-stats',
  templateUrl: './year-stats.component.html',
  styleUrls: ['./year-stats.component.sass']
})
export class YearStatsComponent implements OnInit {
  public barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true
  };
  public barChartLabels: string[];
  public barChartData: Object[];
  public barChartType = 'bar';
  public barChartLegend = false;
  
  constructor(private internshipService: InternshipService) { }

  ngOnInit(): void {
    let yearMap = new Map<string, number>();
    this.internshipService.findAll().subscribe(data => {
      data.forEach(internship => {
        let year = new Date(internship.beginDate).getFullYear().toString();
        let yearNumber = yearMap.get(year);
        if(yearNumber) {
          yearMap.set(year, yearNumber + 1);
        } else {
          yearMap.set(year, 1);
        }
      });
      this.barChartLabels = Array.from(yearMap.keys());
      let chartData = Array.from(yearMap.values());
      this.barChartData = [{ data: chartData, label: 'Nombre de stages effectués' }];
    });
    
  }

}
