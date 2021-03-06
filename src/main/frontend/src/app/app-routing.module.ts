import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InternshipListComponent } from './components/internship-list/internship-list.component';
import { CompanyListComponent } from './components/company-list/company-list.component';
import { AddInternshipComponent } from './components/add-internship/add-internship.component';
import { InternshipDetailsComponent } from './components/internship-details/internship-details.component';
import { CompanyDetailsComponent } from './components/company-details/company-details.component';
import { AddCompanyComponent } from './components/add-company/add-company.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { BoardUserComponent } from './components/board-user/board-user.component';
import { BoardModeratorComponent } from './components/board-moderator/board-moderator.component';
import { BoardAdminComponent } from './components/board-admin/board-admin.component';
import { AuthentificationComponent } from './components/authentification/authentification.component';
import { StudentDetailsComponent } from './components/student-details/student-details.component';
import { StatsComponent } from './components/stats/stats.component';
import { ChartsModule } from 'ng2-charts';
import { BrowserModule } from '@angular/platform-browser';
import { InternshipEditComponent } from './components/add-internship/internship-edit/internship-edit.component';
import { CompanyEditComponent } from './components/add-company/company-edit/company-edit.component';
import { InternshipFormComponent } from './components/internship-form/internship-form.component';
import { ProfileEditComponent } from './components/profile/profile-edit/profile-edit.component';
import { LoadComponent } from './helpers/load/load.component';
import { EditInternshipFormComponent } from './components/internship-form/edit-internship-form/edit-internship-form.component';


const routes: Routes = [
  { path: 'load', component: LoadComponent },
  { path: 'home', component: HomeComponent },
  { path: 'students/:id', component: StudentDetailsComponent },
  { path: 'internships', component: InternshipListComponent },
  { path: 'companies', component: CompanyListComponent },
  { path: 'add-internship', component: InternshipFormComponent },
  { path: 'add-company', component: AddCompanyComponent },
  { path: 'internship/:id', component: InternshipDetailsComponent },
  { path: 'internship/edit/:id', component: EditInternshipFormComponent },
  { path: 'company/:id', component: CompanyDetailsComponent },
  { path: 'company/edit/:id', component: CompanyEditComponent },
  { path: 'authenticate', component: AuthentificationComponent },
  { path: 'profile/:id', component: ProfileComponent },
  { path: 'profile/edit/:id', component: ProfileEditComponent },
  { path: 'my-internships', component: BoardUserComponent },
  { path: 'stats', component: StatsComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
  
];

@NgModule({
  imports: [BrowserModule, RouterModule.forRoot(routes), ChartsModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }