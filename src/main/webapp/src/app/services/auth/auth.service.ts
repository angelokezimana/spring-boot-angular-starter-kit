import {Injectable} from '@angular/core';
import User from '../../models/security/user.model';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {WebApiService} from '../web-api/web-api.service';
import {JwtHelperService} from "@auth0/angular-jwt";
import {Router} from "@angular/router";

export interface Credentials {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: WebApiService, private jwtHelper: JwtHelperService, private router: Router) {
  }

  login(credentials: Credentials): Observable<User | null | undefined> {
    return this.http.post('/api/v1/auth/authenticate', credentials).pipe(
      tap((result: any) => {
        localStorage.setItem('accessToken', result.body.access_token);
        localStorage.setItem('refreshToken', result.body.refresh_token);
      })
    );
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('accessToken');
    return !this.jwtHelper.isTokenExpired(token);
  }

  getUsername(): string | null {
    const token = localStorage.getItem('accessToken');

    if (token) {
      return this.jwtHelper.decodeToken(token).sub;
    }

    return null;
  }

  logout(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    this.router.navigate(['/auth/login'])
  }
}
