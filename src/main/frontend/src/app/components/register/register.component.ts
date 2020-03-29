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
      birthDate: '',
      gender: '',
      role: '',
    });
  }

  get password() {
    return this.registerForm.get('password');
  }

  get role() {
    return this.registerForm.get('role').value;
  }

  onSubmit() {
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

  dateCheck() {
    let date: Date = this.registerForm.get('birthDate').value;

    if ((new Date()).getFullYear() - date.getFullYear() < 17) {
      this.registerForm.get('birthDate').setErrors({
        invalid: true
      });
    }
    else this.registerForm.get('birthDate').setErrors(null);

  }
}
