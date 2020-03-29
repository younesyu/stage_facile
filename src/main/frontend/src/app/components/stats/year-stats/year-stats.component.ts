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
    this.internshipService.countByYear().subscribe(yearMap => {
      this.barChartLabels = Object.getOwnPropertyNames(yearMap);
      let chartData = Object.values(yearMap);
      this.barChartData = [{ data: chartData, label: 'Nombre de stages effectués' }];
    });
    
  }

}
