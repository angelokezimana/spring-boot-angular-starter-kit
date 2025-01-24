import {Component} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {FileService} from "../../../services/file/file.service";
import {FormValidationService} from "../../../services/form-validation/form-validation.service";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {PostService} from "../../../services/post-service/post.service";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {SnackBarService} from "../../../services/snack-bar/snack-bar.service";
import {Router} from "@angular/router";
import PostDetail from "../../../models/blog/post-detail.model";

@Component({
  selector: 'app-post-form',
  standalone: true,
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
export class PostFormComponent {
  postFormGroup = this.formBuilder.group({
    title: ['', [Validators.required, Validators.maxLength(255)]],
    text: ['', Validators.required],
    imageCover: [null as File | null, Validators.required],
    photos: [null as (File | null)[] | null]
  });

  imgCover: { src: string | ArrayBuffer | null, file: File | null, alt: string } = {src: '', file: null, alt: ''};
  images: { src: string | ArrayBuffer | null, file: File | null, alt: string }[] = [];

  constructor(
    private router: Router,
    private fileService: FileService,
    private formBuilder: FormBuilder,
    private postService: PostService,
    private snackbarService: SnackBarService,
    private formValidationService: FormValidationService) {
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
    const formData = new FormData();

    const {title, text, imageCover, photos} = this.postFormGroup.value;

    title && formData.append("title", title);
    text && formData.append("text", text);
    imageCover && formData.append("imageCover", imageCover);
    (photos as (File | null)[])?.flat()?.forEach((file: File | null) => file && formData.append("photos", file));

    this.postService
      .savePost(formData)
      .subscribe({
        next: (result: HttpResponse<PostDetail> | null | undefined) => {
          this.snackbarService.showMessage("Post created successfully", 'success');
          this.router.navigate(['post', result?.body?.id]);
        },
        error: (error: HttpErrorResponse) => {
          this.snackbarService.showMessage(error.error?.value ??
            error.error?.title ??
            error.error?.text ??
            error.error?.imageCover ??
            error.error?.photos, 'error');
          console.log(error.error);
        },
      });
  }

  private updatePhotos() {
    const filesArray = this.images.map(image => image.file);
    this.postFormGroup.patchValue({photos: [filesArray]});
  }
}
