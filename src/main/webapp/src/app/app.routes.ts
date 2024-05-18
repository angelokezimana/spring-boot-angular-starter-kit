import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
    { path: '', redirectTo: 'Home', pathMatch: 'full'},
    { path: 'Home', component: HomeComponent },
];
