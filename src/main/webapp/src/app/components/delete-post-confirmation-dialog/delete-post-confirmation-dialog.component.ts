import { Component } from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'app-delete',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule],
  templateUrl: './delete-post-confirmation-dialog.component.html',
  styleUrl: './delete-post-confirmation-dialog.component.scss'
})
export class DeletePostConfirmationDialogComponent {

}
