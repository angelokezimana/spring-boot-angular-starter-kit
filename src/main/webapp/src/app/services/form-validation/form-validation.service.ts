import { Injectable } from '@angular/core';
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class FormValidationService {

  constructor() { }

  isFieldInvalid(formGroup:FormGroup, name: string): boolean | undefined {
    const formControl = formGroup.get(name);
    return formControl?.invalid && (formControl?.dirty || formControl?.touched)
  }
}
