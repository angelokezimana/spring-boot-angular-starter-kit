import {Component} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {MatCardModule} from "@angular/material/card";

@Component({
    selector: 'app-auth',
    imports: [
        RouterOutlet,
        MatCardModule
    ],
    templateUrl: './auth.component.html',
    styleUrl: './auth.component.scss'
})
export class AuthComponent {

}
