import {Component} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {Router, RouterModule} from "@angular/router";
import {FooterComponent} from "../../../components/footer/footer.component";
import {CommonModule} from "@angular/common";
import {FormValidationService} from "../../../service/form-validation/form-validation.service";
import {Credentials, LoginService} from "../../../service/login/login.service";
import User from "../../../models/security/user.model";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, MatCardModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule,
    MatButtonModule, MatIconModule, MatCheckboxModule, RouterModule, FooterComponent
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginFormGroup = this.formBuilder.group({
    'email': ['', [Validators.required, Validators.email]],
    'password': ['', Validators.required]
  });

  invalidMessage = "";

  constructor(private router: Router,
              private loginService: LoginService,
              private formBuilder: FormBuilder,
              private formValidationService: FormValidationService) {
  }

  isFieldValid(name: string): boolean | undefined {
    return this.formValidationService.isFieldValid(this.loginFormGroup, name);
  }

  login(): void {
    console.log(this.loginFormGroup.value);
    this.loginService.login(this.loginFormGroup.value as Credentials).subscribe({
      next: (result: User | null | undefined) => {
        this.router.navigate(['home']);
      },
      error: error => {
        this.invalidMessage = error;
        console.log(error);
        console.log("yes")
      }
    })
  }
}
