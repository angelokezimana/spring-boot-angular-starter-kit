import {Component} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {FileService} from "../../../services/file/file.service";

@Component({
  selector: 'app-post-form',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatInputModule, MatIconModule, MatFormFieldModule],
  templateUrl: './post-form.component.html',
  styleUrl: './post-form.component.scss'
})
export class PostFormComponent {

  imageCover: { src: string | ArrayBuffer | null, file: File | null, alt: string } = {src: '', file: null, alt: ''};
  images: { src: string | ArrayBuffer | null, file: File | null, alt: string }[] = [];

  constructor(private fileService: FileService) {
  }

  onFileSelected(event: Event): void {
    this.fileService.handleFiles(event, (result, file) => {
      this.images.push({src: result, file, alt: file.name});
    });
  }

  onImageCoverSelected(event: Event): void {
    this.fileService.handleFiles(event, (result, file) => {
      this.imageCover = {src: result, file, alt: file.name};
    });
  }

  onDelete(index: number) {
    this.images.splice(index, 1);
  }
}
