import {ApplicationConfig} from '@angular/core';
import {provideRouter, withComponentInputBinding, withHashLocation} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import { provideHttpClient, withInterceptors } from "@angular/common/http";
import {customInterceptor} from "./interceptor/custom.interceptor";
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([customInterceptor])),
    provideRouter(routes, withHashLocation(), withComponentInputBinding()),
    provideAnimationsAsync(),
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService
  ]
};
