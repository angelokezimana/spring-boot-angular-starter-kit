import {Component, output} from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatMenuModule} from "@angular/material/menu";
import {RouterLink} from "@angular/router";
import {AuthService} from "../../../services/auth/auth.service";

@Component({
    selector: 'app-header',
    imports: [MatToolbarModule, MatIconModule, MatButtonModule, MatMenuModule, RouterLink],
    templateUrl: './header.component.html',
    styleUrl: './header.component.scss'
})
export class HeaderComponent {
  toggleMenu = output();

  email: string | null = this.authService.getUsername();

  constructor(private authService: AuthService) {
  }

  onToggleMenu() {
    this.toggleMenu.emit();
  }

  logout() {
    this.authService.logout();
  }
}
