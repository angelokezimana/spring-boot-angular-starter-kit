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
import {authGuard} from "./guards/auth/auth.guard";
import {ResetPasswordComponent} from "./pages/auth/reset-password/reset-password.component";


export const routes: Routes = [
  {
    path: 'auth', component: AuthComponent, children: [
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterComponent},
      {path: 'activate-account', component: ActivateAccountComponent},
      {path: 'forgot-password', component: ForgotPasswordComponent},
      {path: 'reset-password', component: ResetPasswordComponent}
    ]
  },
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {
    path: '',
    component: DefaultComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'home',
        title: 'Dashboard',
        component: HomeComponent
      },
      {
        path: 'profile',
        title: 'User profile',
        component: ProfileComponent
      },
      {
        path: 'all-posts',
        title: 'Posts list',
        component: AllPostsComponent
      },
      {
        path: 'my-posts',
        title: 'My posts',
        component: MyPostsComponent
      },
      {
        path: 'post-form',
        title: 'Post form',
        component: PostFormComponent
      },
      {
        path: 'post/edit/:id',
        title: 'Post Update',
        component: PostFormComponent
      },
      {
        path: 'post/:id',
        title: 'Post details',
        component: PostDetailComponent
      },
      {
        path: 'admin', children: [
          {
            path: 'users',
            title: 'Users',
            component: UsersComponent
          },
          {
            path: 'roles',
            title: 'Roles',
            component: RolesComponent
          }
        ]
      },
    ]
  },
  {
    path: '**',
    title: 'Page not found',
    component: NotFoundComponent
  }
];
