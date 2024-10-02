import {Component, inject} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {TruncatePipe} from "../../pipe/truncate.pipe";
import {MatIconModule} from "@angular/material/icon";
import {MatDialog} from "@angular/material/dialog";
import {
  DeletePostConfirmationDialogComponent
} from "../../components/delete-post-confirmation-dialog/delete-post-confirmation-dialog.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatCardModule, MatToolbarModule, MatButtonModule, MatIconModule, TruncatePipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  private readonly dialog = inject(MatDialog);

  constructor(
    private router: Router
  ) {
  }

  goToPostDetail(id: number): void {
    this.router.navigate(['post', id]);
  }

  deletePost(): void {
    const dialogRef = this.dialog.open(DeletePostConfirmationDialogComponent);
    dialogRef.afterClosed().subscribe(confirmation => {
      if (confirmation) {
        console.log("Deleted post...");
      }
    });
  }
}
