import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InternshipListComponent } from './components/internship-list/internship-list.component';
import { AppComponent } from './app.component';
import { CompanyListComponent } from './components/company-list/company-list.component';
import { AddInternshipComponent } from './components/add-internship/add-internship.component';
import { InternshipDetailsComponent } from './components/internship-details/internship-details.component';
import { CompanyDetailsComponent } from './components/company-details/company-details.component';
import { AddCompanyComponent } from './components/add-company/add-company.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';
import { BoardUserComponent } from './components/board-user/board-user.component';
import { BoardModeratorComponent } from './components/board-moderator/board-moderator.component';
import { BoardAdminComponent } from './components/board-admin/board-admin.component';
import { AuthentificationComponent } from './components/authentification/authentification.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'internships', component: InternshipListComponent },
  { path: 'companies', component: CompanyListComponent },
  { path: 'add-internship', component: AddInternshipComponent },
  { path: 'add-company', component: AddCompanyComponent },
  { path: 'internship/:id', component: InternshipDetailsComponent },
  { path: 'company/:id', component: CompanyDetailsComponent },
  { path: 'authenticate', component: AuthentificationComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'my-internships', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }