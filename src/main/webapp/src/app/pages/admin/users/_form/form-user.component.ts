import {Component, Inject, OnInit, signal} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {FormValidationService} from "../../../../services/form-validation/form-validation.service";
import User from "../../../../models/security/user.model";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatChipsModule} from "@angular/material/chips";
import {MatIconModule} from "@angular/material/icon";
import {RoleService} from "../../../../services/admin/roles/role.service";
import Role from "../../../../models/security/role.model";
import {MultiSelectComponent} from "../../../../components/shared/multi-select/multi-select.component";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {UserService} from "../../../../services/admin/users/user.service";
import UserRequest from "../../../../models/security/user.request.model";
import {SnackBarService} from "../../../../services/snack-bar/snack-bar.service";

@Component({
    selector: 'app-form-user',
    imports: [MatDialogModule, MatFormFieldModule, ReactiveFormsModule, MatInputModule, MatButtonModule, MatIconModule, MatChipsModule, FormsModule, MatAutocompleteModule, MultiSelectComponent],
    templateUrl: './form-user.component.html',
    styleUrl: './form-user.component.scss'
})
export class FormUserComponent implements OnInit {
  userFormGroup = this.formBuilder.group({
    firstName: ['', [Validators.required, Validators.maxLength(50)]],
    lastName: ['', [Validators.required, Validators.maxLength(50)]],
    email: ['', [Validators.email, Validators.required, Validators.maxLength(100)]]
  });

  readonly roles = signal<Role[]>([]);
  readonly allRoles = signal<Role[]>([]);

  constructor(
    private roleService: RoleService,
    private userService: UserService,
    private dialogRef: MatDialogRef<FormUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { title: string, user?: User },
    private formBuilder: FormBuilder,
    private snackbarService: SnackBarService,
    private formValidationService: FormValidationService
  ) {
  }

  ngOnInit(): void {
    this.loadRoles();
  }

  save(): void {
    const userData = {
      ...this.userFormGroup.value,
      roleIds: this.roles().map(role => role.id),
    };
    console.log(userData);


    this.userService
      .saveUser(userData as UserRequest)
      .subscribe({
        next: (result: HttpResponse<User> | null | undefined) => {
          this.snackbarService.showMessage("User saved successfully", 'success');
        },
        error: (error: HttpErrorResponse) => {
          const err = error.error;

          this.snackbarService.showMessage(err?.value ??
            err?.firstName ??
            err?.lastName ??
            err?.email ??
            err?.roleIds, 'error');
          console.log(error.error);
        },
      });


    this.dialogRef.close(userData);
  }

  isFieldInvalid(name: string): boolean | undefined {
    return this.formValidationService.isFieldInvalid(this.userFormGroup, name);
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
      this.roles.set(userRoles); // This will be passed to ChipMultiSelectComponent
    }
  }

  onRolesChange(roles: Role[]): void {
    this.roles.set(roles);
  }
}
