import {Component} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-post-form',
  standalone: true,
  imports: [MatCardModule, MatButtonModule, MatInputModule, MatIconModule, MatFormFieldModule],
  templateUrl: './post-form.component.html',
  styleUrl: './post-form.component.scss'
})
export class PostFormComponent {

  images: { src: string | ArrayBuffer | null, alt: string }[] = [];

  onFileSelected(event: Event): void {
    const input: HTMLInputElement = event.target as HTMLInputElement;
    if (!input.files) return;

    this.images = [];
    Array.from(input.files).forEach(file => this.preview(file));
  }

  private preview(file: File): void {
    const reader: FileReader = new FileReader();
    reader.onload = (): void => {
      this.images.push({
        src: reader.result,
        alt: file.name
      });
    };
    reader.readAsDataURL(file);
  }
}
