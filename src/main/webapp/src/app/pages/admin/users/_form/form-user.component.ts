import {Component, computed, Inject, model, OnInit, signal} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {FormValidationService} from "../../../../services/form-validation/form-validation.service";
import User from "../../../../models/security/user.model";
import {MatAutocompleteModule, MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatChipInputEvent, MatChipsModule} from "@angular/material/chips";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatIconModule} from "@angular/material/icon";

@Component({
  selector: 'app-form-user',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, ReactiveFormsModule, MatInputModule, MatButtonModule, MatIconModule, MatChipsModule, FormsModule, MatAutocompleteModule],
  templateUrl: './form-user.component.html',
  styleUrl: './form-user.component.scss'
})
export class FormUserComponent implements OnInit {
  userFormGroup = this.formBuilder.group({
    firstName: ['', [Validators.required, Validators.maxLength(50)]],
    lastName: ['', [Validators.required, Validators.maxLength(50)]],
    email: ['', [Validators.email, Validators.required, Validators.maxLength(100)]],
  });

  readonly currentRole = model('');
  readonly roles = signal<string[]>([]);
  readonly allRoles = signal(['Apple', 'Lemon', 'Lime', 'Orange', 'Strawberry', 'Patate douce', 'Pomme de terre', 'Harcicots', "riz"]); // Use a signal for allRoles
  readonly filteredRoles = computed(() => {
    const currentRole = this.currentRole().toLowerCase();
    return currentRole
      ? this.allRoles().filter(role => role.toLowerCase().includes(currentRole))
      : this.allRoles().slice();
  });

  constructor(
    private dialogRef: MatDialogRef<FormUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { title: string, user: undefined | User },
    private formBuilder: FormBuilder,
    private formValidationService: FormValidationService,
    private announcer: LiveAnnouncer) {
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

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our role
    if (value) {
      this.roles.update(roles => [...roles, value]);
      this.removeFromAllRoles(value); // Remove the added role from allRoles
    }

    // Clear the input value
    this.currentRole.set('');
  }

  remove(role: string): void {
    this.roles.update(roles => {
      const index = roles.indexOf(role);
      if (index < 0) {
        return roles;
      }

      roles.splice(index, 1);
      this.announcer.announce(`Removed ${role}`);
      this.allRoles.update(allRoles => [...allRoles, role]); // Add the removed role back to allRoles
      return [...roles];
    });
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const selectedRole = event.option.viewValue;
    this.roles.update(roles => [...roles, selectedRole]);
    this.removeFromAllRoles(selectedRole); // Remove the selected role from allRoles
    this.currentRole.set('');
    event.option.deselect();
  }

  private removeFromAllRoles(role: string): void {
    this.allRoles.update(allRoles => allRoles.filter(f => f !== role));
  }
}
