import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/services/token-storage.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.sass']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  roles: string[];
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  editMode: boolean = false

  constructor(public fb: FormBuilder,
    public router: Router,
    public authService: AuthService,
    public tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken() && !this.editMode) {
      this.router.navigate(['/']);
    }

    this.roles = ["Etudiant", "Enseignant"];

    this.registerForm = this.fb.group({
      username: ['', [
        Validators.email,
      ]],
      password: ['', [
        Validators.minLength(8),
      ]],
      firstName: '',
      lastName: '',
      birthDate: ['', [
        Validators.required,
        Validators.pattern("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\[0-9]\[0-9])")
      ]],
      gender: '',
      role: '',
    });

    this.registerForm.get('birthDate').valueChanges.subscribe(input => {
      if (this.dateCheck(input)) {
        this.registerForm.get('birthDate').setErrors(null);
      }
      else {
          this.registerForm.get('birthDate').setErrors({
            valid: false
          });
        }
      });

  }

  get password() {
    return this.registerForm.get('password');
  }

  get role() {
    return this.registerForm.get('role').value;
  }

  onSubmit() {
    this.registerForm.get('birthDate').setValue(this.parseBirthDate());
    
    if (this.role === "Enseignant") {
      this.registerForm.get('role').setValue("mod");
    }
    else if (this.role === "Etudiant") {
      this.registerForm.get('role').setValue("user");
    }
    this.authService.register(this.registerForm.value).subscribe(
      data => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        setTimeout(() => {
          window.location.reload();
          this.router.navigate(['/']);
        }, 2000);

      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

  parseBirthDate(): string {
    let dateStr: String = this.registerForm.get('birthDate').value;
    let year = dateStr.substring(dateStr.lastIndexOf("/") + 1);
    let day = dateStr.substring(0, dateStr.indexOf("/"));
    if (day.length < 2) day = "0" + day
    let month = dateStr.substring(dateStr.indexOf("/") + 1, dateStr.lastIndexOf("/"));
    if (month.length < 2) month = "0" + month
    return (year + "-" + month + "-" + day);
  }

  dateCheck(date: string) {
    let year : number;
    try {
      year = parseInt(date.substring(date.lastIndexOf("/") + 1));
    } catch {
      return false;
    }

    if ((new Date()).getFullYear() - year < 17) {
      return false;
    }

    else {
      return true;
    } 

  }
}
