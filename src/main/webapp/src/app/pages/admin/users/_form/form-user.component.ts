import {Component, computed, Inject, OnInit, signal, WritableSignal} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {FormValidationService} from "../../../../services/form-validation/form-validation.service";
import User from "../../../../models/security/user.model";
import {MatAutocompleteModule, MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {MatChipsModule} from "@angular/material/chips";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatIconModule} from "@angular/material/icon";
import {RoleService} from "../../../../services/admin/roles/role.service";
import Role from "../../../../models/security/role.model";
import {toSignal} from "@angular/core/rxjs-interop";

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
    email: ['', [Validators.email, Validators.required, Validators.maxLength(100)]]
  });

  currentRole = new FormControl<Role | null>(null);
  private readonly currentRoleValue = toSignal(this.currentRole.valueChanges, {initialValue: null}); // Convert FormControl valueChanges to Signal

  readonly roles: WritableSignal<Role[]> = signal<Role[]>([]); // User's assigned roles
  readonly allRoles = signal<Role[]>([]); // All available roles fetched from the service

  readonly filteredRoles = computed(() => {
    const currentRoleName: string | undefined = this.currentRoleValue()?.name;
    const userRoles: Role[] = this.roles();
    const availableRoles: Role[] = this.allRoles();

    const rolesNotAssigned: Role[] = availableRoles.filter(
      (role: Role) => !userRoles.some(userRole => userRole.id === role.id)
    );

    if (!currentRoleName) {
      return rolesNotAssigned;
    }

    const lowerCaseRole = currentRoleName.toLowerCase();
    return rolesNotAssigned.filter(role =>
      role.name.toLowerCase().includes(lowerCaseRole)
    );
  });

  constructor(
    private roleService: RoleService,
    private dialogRef: MatDialogRef<FormUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { title: string, user?: User },
    private formBuilder: FormBuilder,
    private formValidationService: FormValidationService,
    private announcer: LiveAnnouncer
  ) {
  }

  ngOnInit(): void {
    this.loadRoles();
  }

  save(): void {
    const userData = {
      ...this.userFormGroup.value,
      roles: this.roles(),
    };
    console.log(userData);
    this.dialogRef.close(userData);
  }

  isFieldInvalid(name: string): boolean | undefined {
    return this.formValidationService.isFieldInvalid(this.userFormGroup, name);
  }

  remove(role: Role): void {
    this.roles.update(roles => roles.filter(r => r.id !== role.id)); // Use id for uniqueness
    this.announcer.announce(`Removed ${role.name}`);
    // Only add back if not already in allRoles
    this.allRoles.update(allRoles => {
      if (!allRoles.some(r => r.id === role.id)) {
        return [...allRoles, role];
      }
      return allRoles;
    });
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const selectedRole = event.option.value as Role;
    this.roles.update(roles => {
      if (!roles.some(r => r.id === selectedRole.id)) { // Prevent duplicates in roles
        return [...roles, selectedRole];
      }
      return roles;
    });
    this.allRoles.update(allRoles => allRoles.filter(r => r.id !== selectedRole.id));
    this.currentRole.reset(null);
    event.option.deselect();
  }

  private loadRoles(): void {
    this.roleService.getAllRoles().subscribe(response => {
      if (response.body) {
        this.allRoles.set(response.body);
        if (this.data?.user) {
          this.edit();
        }
      }
    });
  }

  private edit(): void {
    if (this.data.user) {
      this.userFormGroup.patchValue(this.data.user);
      const userRoles: Role[] = this.data.user.roles || [];
      this.roles.set(userRoles);
    }
  }
}
