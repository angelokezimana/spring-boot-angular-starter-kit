import { Component } from '@angular/core';
import {MatToolbar} from "@angular/material/toolbar";

@Component({
    selector: 'app-default-footer',
    imports: [
        MatToolbar
    ],
    templateUrl: './default-footer.component.html',
    styleUrl: './default-footer.component.scss'
})
export class DefaultFooterComponent {
  currentYear: number = new Date().getFullYear();
}
