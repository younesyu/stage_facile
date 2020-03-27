import { Component, OnInit } from '@angular/core';
import { InternshipService } from 'src/app/services/internship.service';

@Component({
  selector: 'app-gender-stats',
  templateUrl: './gender-stats.component.html',
  styleUrls: ['./gender-stats.component.sass']
})
export class GenderStatsComponent implements OnInit {
  
  maleCount: number;
  femaleCount: number;

  public pieChartLabels = ['Femmes', 'Hommes'];
  public pieChartData: number[];
  public pieChartType = 'pie';

  constructor(private internshipService: InternshipService) { }

  ngOnInit(): void {
    this.internshipService.countByGender().subscribe(data => {
      this.maleCount = data.males;
      this.femaleCount = data.females;
      this.pieChartData = [this.femaleCount, this.maleCount];
    });
  }

}
