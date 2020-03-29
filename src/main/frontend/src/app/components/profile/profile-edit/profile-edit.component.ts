import { Component, OnInit } from '@angular/core';
import { RegisterComponent } from '../../register/register.component';
import { FormBuilder, FormControl } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.sass']
})
export class ProfileEditComponent extends RegisterComponent implements OnInit {
  id: number;

  constructor(public fb: FormBuilder,
    public router: Router, 
    private route: ActivatedRoute,
    public authService: AuthService,
    private userService: UserService,
    public tokenStorageService: TokenStorageService) {
    super(fb, router, authService, tokenStorageService);
    this.editMode = true;
  }

  ngOnInit(): void {
    super.ngOnInit();
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.userService.get(this.id).subscribe(data => {
      this.registerForm.patchValue(data);

      this.registerForm.get('username').setValue(data.email);
      this.registerForm.get('username').disable();
      let birthDate = this.parseDate(data.birthDate.toString())
      this.registerForm.get('birthDate').setValue(birthDate);
      this.registerForm.get('birthDate').valueChanges.subscribe(console.log);
    });
  }

  parseDate(dateStr: string): Date {
    let date = new Date();
    console.log(dateStr.substring(0, 4))
    console.log(dateStr.substring(5, 7))
    console.log(dateStr.substring(8, 10))
    console.log(dateStr)
    date.setFullYear(+(dateStr.substring(0, 4)), +(dateStr.substring(5, 7)) - 1,
    +(dateStr.substring(8, 10)))
    return date;
  }

}
