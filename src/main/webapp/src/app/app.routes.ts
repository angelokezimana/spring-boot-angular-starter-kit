import {Routes} from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {RegisterComponent} from "./pages/auth/register/register.component";
import {LoginComponent} from "./pages/auth/login/login.component";
import {AuthComponent} from "./layouts/auth/auth.component";
import {ActivateAccountComponent} from "./pages/auth/activate-account/activate-account.component";
import {DefaultComponent} from "./layouts/default/default.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {ForgotPasswordComponent} from "./pages/auth/forgot-password/forgot-password.component";
import {MyPostsComponent} from "./pages/posts/my-posts/my-posts.component";
import {PostFormComponent} from "./pages/posts/post-form/post-form.component";
import {UsersComponent} from "./pages/admin/users/users.component";
import {RolesComponent} from "./pages/admin/roles/roles.component";
import {ProfileComponent} from "./pages/profile/profile.component";
import {PostDetailComponent} from "./pages/posts/post-detail/post-detail.component";
import {AllPostsComponent} from "./pages/posts/all-posts/all-posts.component";


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
      {path: 'profile', component: ProfileComponent},
      {path: 'all-posts', component: AllPostsComponent},
      {path: 'my-posts', component: MyPostsComponent},
      {path: 'post-form', component: PostFormComponent},
      {path: 'post/:id', component: PostDetailComponent},
      {
        path: 'admin', children: [
          {path: 'users', component: UsersComponent},
          {path: 'roles', component: RolesComponent}
        ]
      },
    ]
  },
  {path: '**', component: NotFoundComponent}
];
