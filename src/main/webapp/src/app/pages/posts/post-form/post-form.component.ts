import {Component, OnInit} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {FileService} from "../../../services/file/file.service";
import {FormValidationService} from "../../../services/form-validation/form-validation.service";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {PostService} from "../../../services/blog/posts/post.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {SnackBarService} from "../../../services/snack-bar/snack-bar.service";
import {ActivatedRoute, Router} from "@angular/router";
import PostDetail from "../../../models/blog/post-detail.model";

@Component({
    selector: 'app-post-form',
    imports: [
        MatCardModule,
        MatButtonModule,
        MatInputModule,
        MatIconModule,
        ReactiveFormsModule,
        MatFormFieldModule
    ],
    templateUrl: './post-form.component.html',
    styleUrl: './post-form.component.scss'
})
export class PostFormComponent implements OnInit {
  postFormGroup = this.formBuilder.group({
    title: ['', [Validators.required, Validators.maxLength(255)]],
    text: ['', Validators.required],
    imageCover: [null as File | null, Validators.required],
    photos: [null as (File | null)[] | null]
  });

  post: PostDetail | null = null;

  imgCover: { src: string | ArrayBuffer | null, file: File | null, alt: string } = {src: '', file: null, alt: ''};
  images: { src: string | ArrayBuffer | null, file: File | null, alt: string }[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fileService: FileService,
    private formBuilder: FormBuilder,
    private postService: PostService,
    private snackbarService: SnackBarService,
    private formValidationService: FormValidationService) {
  }

  ngOnInit() {
    const postId = this.route.snapshot.paramMap.get('id');

    if (postId) {
      this.postService.getPostById(+postId).subscribe({
        next: (response: HttpResponse<PostDetail>) => {
          this.post = response.body;
          this.postFormGroup.patchValue({
            title: this.post?.title,
            text: this.post?.text
          });
          this.postFormGroup.get('imageCover')?.clearValidators();
          this.postFormGroup.get('imageCover')?.updateValueAndValidity();
        },
        error: (error: HttpErrorResponse) => {
          console.error(error);
          this.snackbarService.showMessage(error.error?.value, 'error');
          this.router.navigate(['home']);
        },
      });
    }
  }

  isFieldInvalid(name: string): boolean | undefined {
    return this.formValidationService.isFieldInvalid(this.postFormGroup, name);
  }

  onFileSelected(event: Event): void {
    this.fileService.handleFiles(event, (result, file) => {
      this.images.push({src: result, file, alt: file.name});
      this.updatePhotos();
    });
  }

  onImageCoverSelected(event: Event): void {
    this.fileService.handleFiles(event, (result, file) => {
      this.imgCover = {src: result, file, alt: file.name};
      this.postFormGroup.patchValue({imageCover: file});
    })
  }

  onDelete(index: number) {
    this.images.splice(index, 1);
    this.updatePhotos();
  }

  save() {
    const formData: FormData = this.fetchFormData();

    this.postService
      .savePost(formData)
      .subscribe({
        next: (result: HttpResponse<PostDetail> | null | undefined) => {
          this.snackbarService.showMessage("Post created successfully", 'success');
          this.router.navigate(['post', result?.body?.id]);
        },
        error: (error: HttpErrorResponse) => {
          const err = error.error;

          this.snackbarService.showMessage(err?.value ??
            err?.title ??
            err?.text ??
            err?.imageCover ??
            err?.photos, 'error');
          console.log(error.error);
        },
      });
  }

  update() {
    const formData: FormData = this.fetchFormData();

    this.postService
      .updatePost(formData, +(this.post?.id as number))
      .subscribe({
        next: (result: HttpResponse<PostDetail> | null | undefined) => {
          this.snackbarService.showMessage("Post updated successfully", 'success');
          this.router.navigate(['post', result?.body?.id]);
        },
        error: (error: HttpErrorResponse) => {
          const err = error.error;

          this.snackbarService.showMessage(err?.value ??
            err?.title ??
            err?.text ??
            err?.imageCover, 'error');
          console.log(error.error);
        },
      });
  }

  private updatePhotos() {
    const filesArray = this.images.map(image => image.file);
    this.postFormGroup.patchValue({photos: [filesArray]});
  }

  private fetchFormData(): FormData {
    const formData = new FormData();

    const {title, text, imageCover, photos} = this.postFormGroup.value;

    title && formData.append("title", title);
    text && formData.append("text", text);
    imageCover && formData.append("imageCover", imageCover);
    (photos as (File | null)[])?.flat()?.forEach((file: File | null) => file && formData.append("photos", file));

    return formData;
  }
}
