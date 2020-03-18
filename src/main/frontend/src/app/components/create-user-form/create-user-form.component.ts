import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-create-user-form',
  templateUrl: './create-user-form.component.html',
  styleUrls: ['./create-user-form.component.sass']
})
export class CreateUserFormComponent implements OnInit {

  name = new FormControl('');

  
  constructor() { }

  ngOnInit(): void {
  }

}
