import { Component } from '@angular/core';
import { TokenStorageService } from './services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  private role: string;
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  showUserBoard = false;
  username: string;
  user;

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      this.user = this.tokenStorageService.getUser();
      this.role = this.user.role;

      this.showAdminBoard = this.role.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.role.includes('ROLE_MODERATOR') || this.role.includes('ROLE_ADMIN');
      this.showUserBoard = this.role.includes('ROLE_USER');

      this.username = this.user.username;
    }
  }

  logout() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
