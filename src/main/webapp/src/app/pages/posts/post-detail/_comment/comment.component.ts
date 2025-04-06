import {Component, effect, input, signal, WritableSignal} from '@angular/core';
import {MatCard, MatCardActions, MatCardContent} from "@angular/material/card";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {SnackBarService} from "../../../../services/snack-bar/snack-bar.service";
import {FormValidationService} from "../../../../services/form-validation/form-validation.service";
import {MatDialog} from "@angular/material/dialog";
import {DeleteConfirmationDialogComponent} from "../../../../components/shared/delete-confirmation-dialog/delete-confirmation-dialog.component";
import Comment from "../../../../models/blog/comment.model";
import {CommentService} from "../../../../services/blog/comments/comment.service";
import {DatePipe} from "@angular/common";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import PostDetail from "../../../../models/blog/post-detail.model";

@Component({
    selector: 'app-comment',
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

  post = input.required<PostDetail | null>();
  comments: WritableSignal<Comment[]> = signal<Comment[]>([]);
  numberOfComments: WritableSignal<number> = signal<number>(0);

  constructor(
    private commentService: CommentService,
    private snackbarService: SnackBarService,
    private formValidationService: FormValidationService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog) {

    effect(() => {
      if (this.post()) {
        this.commentService.getCommentsByPostId(this.post()?.id as number)
          .subscribe((response: HttpResponse<Comment[]>) => {
            this.comments.set(response.body as Comment[]);
            this.numberOfComments.set(this.post()?.numberOfComments as number);
          });
        console.log(`===============postId:${this.post()?.id}================`)
      }
    });

  }

  isFieldInvalid(name: string): boolean | undefined {
    return this.formValidationService.isFieldInvalid(this.commentFormGroup, name);
  }

  save(): void {
    this.commentService
      .saveComment(this.post()?.id as number, this.commentFormGroup.value as String)
      .subscribe({
        next: (result: HttpResponse<Comment>) => {
          this.comments.update((comments: Comment[]): Comment[] => [result.body as Comment, ...comments]);
          this.numberOfComments.set(this.numberOfComments() + 1);
          this.snackbarService.showMessage("Comment created successfully", 'success');
          this.resetForm();
        },
        error: (error: HttpErrorResponse) => {
          const err = error.error;
          this.snackbarService.showMessage(err?.value ?? err?.text, 'error');
        },
      });
  }

  update(id: number): void {

  }

  editComment(id: number) {

  }

  deleteComment(id: number) {
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
    this.commentFormGroup.reset();
    this.commentFormGroup.updateValueAndValidity();
    this.commentFormGroup.markAsPristine();
    this.commentFormGroup.markAsUntouched();
  }
}
