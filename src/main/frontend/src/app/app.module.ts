import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

/* Pagination */
import { NgxPaginationModule } from 'ngx-pagination';

/* Angular Material */
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatInputModule } from '@angular/material/input'
import { MatSelectModule } from '@angular/material/select'
import { MatButtonModule } from '@angular/material/button'
import { MatCheckboxModule } from '@angular/material/checkbox'
import { MatChipsModule } from '@angular/material/chips'
import { MatDatepickerModule } from '@angular/material/datepicker'
import { MatNativeDateModule } from '@angular/material/core';
import { MatListModule } from '@angular/material/list';
import { MatRadioModule } from '@angular/material/radio'; 
import { MatTabsModule } from '@angular/material/tabs'; 
import { MatPaginatorModule } from '@angular/material/paginator'; 
import { MatSortModule } from '@angular/material/sort'; 
import { MatTableModule } from '@angular/material/table'; 
import { MatDialogModule } from '@angular/material/dialog'; 
import { MatAutocompleteModule } from '@angular/material/autocomplete'; 
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatStepperModule } from '@angular/material/stepper'; 

import { authInterceptorProviders } from './helpers/auth.interceptor';

/* Chart.js */
import { ChartsModule } from 'ng2-charts';

/* App components */
import { CreateUserFormComponent } from './components/create-user-form/create-user-form.component';
import { InternshipListComponent } from './components/internship-list/internship-list.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { CompanyListComponent } from './components/company-list/company-list.component';
import { AddInternshipComponent } from './components/add-internship/add-internship.component';
import { InternshipDetailsComponent } from './components/internship-details/internship-details.component';
import { CompanyDetailsComponent } from './components/company-details/company-details.component';
import { AddCompanyComponent } from './components/add-company/add-company.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { BoardAdminComponent } from './components/board-admin/board-admin.component';
import { BoardModeratorComponent } from './components/board-moderator/board-moderator.component';
import { BoardUserComponent } from './components/board-user/board-user.component';
import { AuthentificationComponent } from './components/authentification/authentification.component';
import { StudentDetailsComponent } from './components/student-details/student-details.component';
import { StatsComponent } from './components/stats/stats.component';
import { GenderStatsComponent } from './components/stats/gender-stats/gender-stats.component';
import { YearStatsComponent } from './components/stats/year-stats/year-stats.component';
import { CountryStatsComponent } from './components/stats/country-stats/country-stats.component';
import { AdminUserListComponent } from './components/board-admin/admin-user-list/admin-user-list.component';
import { AdminModListComponent } from './components/board-admin/admin-mod-list/admin-mod-list.component';
import { AdminAdminListComponent } from './components/board-admin/admin-admin-list/admin-admin-list.component';
import { InternshipEditComponent } from './components/add-internship/internship-edit/internship-edit.component';
import { DeletionDialogComponent } from './components/deletion-dialog/deletion-dialog.component';
import { CompanyEditComponent } from './components/add-company/company-edit/company-edit.component';
import { IndustryStatsComponent } from './components/stats/industry-stats/industry-stats.component';
import { AddReviewComponent } from './components/add-review/add-review.component';
import { InternshipFormComponent } from './components/internship-form/internship-form.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateUserFormComponent,
    InternshipListComponent,
    NavbarComponent,
    CompanyListComponent,
    AddInternshipComponent,
    InternshipDetailsComponent,
    CompanyDetailsComponent,
    AddCompanyComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    AuthentificationComponent,
    StudentDetailsComponent,
    StatsComponent,
    GenderStatsComponent,
    YearStatsComponent,
    CountryStatsComponent,
    AdminUserListComponent,
    AdminModListComponent,
    AdminAdminListComponent,
    InternshipEditComponent,
    DeletionDialogComponent,
    CompanyEditComponent,
    IndustryStatsComponent,
    AddReviewComponent,
    InternshipFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatNativeDateModule,
    ChartsModule,

    /* UI modules */
    NgxPaginationModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatListModule,
    MatRadioModule,
    MatTabsModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatDialogModule,
    MatAutocompleteModule,
    MatSnackBarModule,
    MatStepperModule,
  ],
  providers: [MatDatepickerModule, authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
