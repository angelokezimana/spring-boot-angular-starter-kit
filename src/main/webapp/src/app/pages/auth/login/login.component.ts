import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { Router, RouterModule } from '@angular/router';
import { FooterComponent } from '../../../components/shared/footer/footer.component';
import { CommonModule } from '@angular/common';
import { FormValidationService } from '../../../services/form-validation/form-validation.service';
import {
  Credentials,
  AuthService,
} from '../../../services/auth/auth.service';
import User from '../../../models/security/user.model';
import { HttpErrorResponse } from '@angular/common/http';
import { SnackBarService } from '../../../services/snack-bar/snack-bar.service';
import { finalize } from 'rxjs';

@Component({
    selector: 'app-login',
    imports: [
        CommonModule,
        MatCardModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        MatCheckboxModule,
        RouterModule,
        MatProgressSpinnerModule,
        FooterComponent,
    ],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginFormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });

  isLoading: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private formValidationService: FormValidationService,
    private snackbarService: SnackBarService
  ) {}

  isFieldInvalid(name: string): boolean | undefined {
    return this.formValidationService.isFieldInvalid(this.loginFormGroup, name);
  }

  login(): void {
    this.isLoading = true;

    this.authService
      .login(this.loginFormGroup.value as Credentials)
      .pipe(finalize(() => this.isLoading = false))
      .subscribe({
        next: (result: User | null | undefined) => {
          this.router.navigate(['home']);
        },
        error: (error: HttpErrorResponse) => {
          this.loginFormGroup.get('password')?.reset('', { emitEvent: false });
          this.snackbarService.showMessage(error.error?.value, 'error');
        },
      });
  }
}
