import {Component, input} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {TruncatePipe} from "../../pipes/truncate/truncate.pipe";
import {MatDialog} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {DeleteConfirmationDialogComponent} from "../shared/delete-confirmation-dialog/delete-confirmation-dialog.component";
import Post from "../../models/blog/post.model";
import {DatePipe} from "@angular/common";

@Component({
    selector: 'app-post-card',
    imports: [MatCardModule, MatIconModule, MatButtonModule, TruncatePipe, DatePipe],
    templateUrl: './post-card.component.html',
    styleUrl: './post-card.component.scss'
})
export class PostCardComponent {
  post = input.required<Post>();

  constructor(
    private router: Router,
    private dialog: MatDialog
  ) {
  }

  goToPostDetail(id: number): void {
    this.router.navigate(['post', id]);
  }

  deletePost(id: number): void {
    const dialogRef = this.dialog.open(DeleteConfirmationDialogComponent, {
      data: {
        title: 'Delete Post',
        message: 'Do you want to delete the selected post?'
      }
    });

    dialogRef.afterClosed().subscribe(confirmation => {
      if (confirmation) {
        console.log("Deleted post...");
      }
    });
  }
}
