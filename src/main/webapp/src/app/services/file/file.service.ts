import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor() {
  }

  handleFiles(event: Event, callback: (result: string | ArrayBuffer | null, file: File) => void): void {
    const files = this.getFilesFromEvent(event);
    files?.forEach(file => {
      this.readFile(file, (result) => callback(result, file));
    });
  }

  private getFilesFromEvent(event: Event): File[] | null {
    const input = event.target as HTMLInputElement;
    return input.files ? Array.from(input.files) : null;
  }

  private readFile(file: File, callback: (result: string | ArrayBuffer | null) => void): void {
    const reader = new FileReader();
    reader.onload = () => callback(reader.result);
    reader.readAsDataURL(file);
  }
}
