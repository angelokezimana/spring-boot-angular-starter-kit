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
import {RoleService} from "../../../../services/admin/roles/role.service";
import Role from "../../../../models/security/role.model";

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

  readonly currentRole = model<Role>();
  readonly roles = signal<Role[]>([]);
  readonly allRoles = signal<Role[]>([]); // Use a signal for allRoles
  readonly filteredRoles = computed(() => {
    const currentRole = this.currentRole()?.name.toLowerCase();
    return currentRole
      ? this.allRoles().filter(role => role.name.toLowerCase().includes(currentRole))
      : this.allRoles().slice();
  });

  constructor(
    private roleService: RoleService,
    private dialogRef: MatDialogRef<FormUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { title: string, user: undefined | User },
    private formBuilder: FormBuilder,
    private formValidationService: FormValidationService,
    private announcer: LiveAnnouncer) {
  }

  ngOnInit(): void {
    if (this.data.user) {
      this.userFormGroup.patchValue(this.data.user);
      this.roles.set(this.data.user.roles || []);
    }

    this.loadRoles();
  }

  save() {
    // saving user
    const userData = {
      ...this.userFormGroup.value,
      roles: this.roles(),
    };

    console.log(userData);
    this.dialogRef.close();
  }

  isFieldInvalid(name: string): boolean | undefined {
    return this.formValidationService.isFieldInvalid(this.userFormGroup, name);
  }

  // add(event: MatChipInputEvent): void {
  //   const value = event.value;
  //
  //   // Add our role
  //   if (value) {
  //     this.roles.update(roles => [...roles, value]);
  //     this.removeFromAllRoles(value); // Remove the added role from allRoles
  //   }
  //
  //   // Clear the input value
  //   this.currentRole.set('');
  // }

  remove(role: Role): void {
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
    const selectedRole = event.option.value as Role;
    this.roles.update(roles => [...roles, selectedRole]);
    this.removeFromAllRoles(selectedRole); // Remove the selected role from allRoles
    this.currentRole.set(undefined);
    event.option.deselect();
  }

  private removeFromAllRoles(role: Role): void {
    this.allRoles.update(allRoles => allRoles.filter(r => r !== role));
  }

  private loadRoles() {
    this.roleService.getAllRoles().subscribe(response => {
      if (response.body) {
        this.allRoles.set(response.body);
      }
    });
  }
}
