import { Injectable, signal } from '@angular/core';
import User from '../../models/security/user.model';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { WebApiService } from '../web-api/web-api.service';

export interface Credentials {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  user = signal<User | null | undefined>(undefined);

  constructor(private http: WebApiService) {}

  login(credentials: Credentials): Observable<User | null | undefined> {
    return this.http.post('/api/v1/auth/authenticate', credentials).pipe(
      tap((result: any) => {
        localStorage.setItem('accessToken', result.body.access_token);
        localStorage.setItem('refreshToken', result.body.refresh_token);
      })
    );
  }
}
