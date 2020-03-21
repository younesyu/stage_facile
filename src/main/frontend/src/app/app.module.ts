import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

/* Pagination */
import { NgxPaginationModule } from 'ngx-pagination';

/* Angular Material */
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
import { MatTableModule } from '@angular/material/table'; 

import { authInterceptorProviders } from './helpers/auth.interceptor';

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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatNativeDateModule,

    /* UI modules */
    NgxPaginationModule,
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
    MatPaginatorModule,
    MatTableModule,
  ],
  providers: [MatDatepickerModule, authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
