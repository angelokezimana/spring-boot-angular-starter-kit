import {Component} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {MatCardModule} from "@angular/material/card";

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    RouterOutlet,
    MatCardModule
  ],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {

}
