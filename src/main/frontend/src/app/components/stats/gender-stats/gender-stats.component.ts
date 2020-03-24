import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-gender-stats',
  templateUrl: './gender-stats.component.html',
  styleUrls: ['./gender-stats.component.sass']
})
export class GenderStatsComponent implements OnInit {
  
  public pieChartLabels = ['Femmes', 'Hommes', 'Non défini'];
  public pieChartData: number[];
  public pieChartType = 'pie';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    let nbMen = 0;
    let nbWomen = 0;
    let nbNonAssigned = 0;

    this.userService.findAll().subscribe(data => {
      data.forEach(user => {
        if(user.gender) nbMen++;
        else if(!user.gender) nbWomen++;
        else if(user.gender == null) nbNonAssigned++;
      });
      this.pieChartData = [nbMen, nbWomen, nbNonAssigned];
    });
  }

}
