import {Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {RegisterComponent} from "./pages/auth/register/register.component";
import {LoginComponent} from "./pages/auth/login/login.component";
import {AuthComponent} from "./layouts/auth/auth.component";
import {ActivateAccountComponent} from "./pages/auth/activate-account/activate-account.component";
import {DefaultComponent} from "./layouts/default/default.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {ForgotPasswordComponent} from "./pages/auth/forgot-password/forgot-password.component";


export const routes: Routes = [
  {
    path: 'auth', component: AuthComponent, children: [
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterComponent},
      {path: 'activate-account', component: ActivateAccountComponent},
      {path: 'forgot-password', component: ForgotPasswordComponent}
    ]
  },
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {
    path: '', component: DefaultComponent, children: [
      {path: 'home', component: HomeComponent},
      {
        path: 'posts', children: [
          {path: '', component: HomeComponent},
          {path: 'add', component: HomeComponent}
        ]
      },
      {
        path: 'comments', children: [
          {path: '', component: HomeComponent},
          {path: 'add', component: HomeComponent}
        ]
      },
    ]
  },
  {path: '**', component: NotFoundComponent}
];
