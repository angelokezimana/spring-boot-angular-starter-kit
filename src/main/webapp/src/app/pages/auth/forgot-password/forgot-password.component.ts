import {Component} from '@angular/core';
import {FooterComponent} from "../../../components/shared/footer/footer.component";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";

@Component({
    selector: 'app-forgot-password',
    imports: [
        MatCardModule, FormsModule, MatFormFieldModule, MatInputModule,
        MatButtonModule, MatIconModule, RouterModule, FooterComponent
    ],
    templateUrl: './forgot-password.component.html',
    styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {

}
