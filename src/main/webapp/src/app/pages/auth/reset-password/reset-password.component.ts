import { Component } from '@angular/core';
import {FooterComponent} from "../../../components/shared/footer/footer.component";
import {MatAnchor, MatButton} from "@angular/material/button";
import {
    MatCardActions,
    MatCardContent,
    MatCardFooter,
    MatCardHeader,
    MatCardSubtitle,
    MatCardTitle
} from "@angular/material/card";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {RouterLink} from "@angular/router";

@Component({
    selector: 'app-reset-password',
    imports: [
        FooterComponent,
        MatAnchor,
        MatButton,
        MatCardActions,
        MatCardContent,
        MatCardFooter,
        MatCardHeader,
        MatCardSubtitle,
        MatCardTitle,
        MatFormField,
        MatInput,
        MatLabel,
        RouterLink
    ],
    templateUrl: './reset-password.component.html',
    styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent {

}
