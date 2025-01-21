import {Component, effect, input} from '@angular/core';
import {MatCard, MatCardActions, MatCardContent} from "@angular/material/card";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {SnackBarService} from "../../services/snack-bar/snack-bar.service";
import {FormValidationService} from "../../services/form-validation/form-validation.service";
import {MatDialog} from "@angular/material/dialog";
import {DeleteConfirmationDialogComponent} from "../delete-confirmation-dialog/delete-confirmation-dialog.component";
import Comment from "../../models/blog/comment.model";
import {CommentService} from "../../services/comment-service/comment.service";
import {DatePipe} from "@angular/common";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-comment',
  standalone: true,
  imports: [
    MatCard,
    MatCardActions,
    MatCardContent,
    MatIcon,
    MatIconButton,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatError,
    DatePipe
  ],
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.scss'
})
export class CommentComponent {
  commentFormGroup = this.formBuilder.group({
    text: ['', Validators.required]
  });

  postId = input.required<Number>();
  numberOfComments: Number = 0;
  comments: Comment[] = [];

  constructor(
    private commentService: CommentService,
    private snackbarService: SnackBarService,
    private formValidationService: FormValidationService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog) {

    effect(() => {
      if (this.postId()) {
        this.commentService.getCommentsByPostId(this.postId())
          .subscribe((results: any) => {
            this.comments = results.body;
            this.numberOfComments = this.comments.length;
          });
        console.log(`===============postId:${this.postId()}================`)
      }
    });

  }

  isFieldInvalid(name: string): boolean | undefined {
    return this.formValidationService.isFieldInvalid(this.commentFormGroup, name);
  }

  save(): void {
    this.commentService
      .saveComment(this.postId(), this.commentFormGroup.value as String)
      .subscribe({
        next: (result: HttpResponse<Comment>) => {
          this.comments.unshift(result.body as Comment);
          this.numberOfComments = this.comments.length;
          this.snackbarService.showMessage("Comment created successfully", 'success');
          this.resetForm();
        },
        error: (error: HttpErrorResponse) => {
          this.snackbarService.showMessage(error.error?.value, 'error');
        },
      });
  }

  update(): void {

  }

  editComment(id: Number) {

  }

  deleteComment(id: Number) {
    const dialogRef = this.dialog.open(DeleteConfirmationDialogComponent, {
      data: {
        title: 'Delete Comment',
        message: 'Do you want to delete the selected comment?'
      }
    });

    dialogRef.afterClosed().subscribe(confirmation => {
      if (confirmation) {
        console.log("Deleted comment...");
      }
    });
  }

  private resetForm(): void {
    this.commentFormGroup.controls.text.reset();
    this.commentFormGroup.controls.text.setErrors(null);
  }
}
