import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InternshipListComponent } from './components/internship-list/internship-list.component';
import { AppComponent } from './app.component';
import { CompanyListComponent } from './components/company-list/company-list.component';
import { AddInternshipComponent } from './components/add-internship/add-internship.component';
import { InternshipDetailsComponent } from './components/internship-details/internship-details.component';
import { CompanyDetailsComponent } from './components/company-details/company-details.component';
import { AddCompanyComponent } from './components/add-company/add-company.component';


const routes: Routes = [
  { path: 'internships', component: InternshipListComponent },
  { path: 'companies', component: CompanyListComponent },
  { path: 'add-internship', component: AddInternshipComponent },
  { path: 'add-company', component: AddCompanyComponent },
  { path: 'internship/:id', component: InternshipDetailsComponent },
  { path: 'company/:id', component: CompanyDetailsComponent },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }