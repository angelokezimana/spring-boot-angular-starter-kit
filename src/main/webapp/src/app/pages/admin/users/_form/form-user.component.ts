import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {FormValidationService} from "../../../../services/form-validation/form-validation.service";
import User from "../../../../models/security/user.model";

@Component({
  selector: 'app-form-user',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, ReactiveFormsModule, MatInputModule, MatButtonModule],
  templateUrl: './form-user.component.html',
  styleUrl: './form-user.component.scss'
})
export class FormUserComponent implements OnInit {
  userFormGroup = this.formBuilder.group({
    firstName: ['', [Validators.required, Validators.maxLength(50)]],
    lastName: ['', [Validators.required, Validators.maxLength(50)]],
    email: ['', [Validators.email, Validators.required, Validators.maxLength(100)]],
  });

  constructor(
    private dialogRef: MatDialogRef<FormUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { title: string, user: undefined | User },
    private formBuilder: FormBuilder,
    private formValidationService: FormValidationService) {
  }

  ngOnInit(): void {
    if (this.data.user) {
      this.userFormGroup.patchValue(this.data.user);
    }
  }

  save() {
    // saving user

    this.dialogRef.close();
  }

  isFieldInvalid(name: string): boolean | undefined {
    return this.formValidationService.isFieldInvalid(this.userFormGroup, name);
  }
}
