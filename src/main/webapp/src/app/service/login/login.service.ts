import {Injectable, signal} from '@angular/core';
import User from "../../models/security/user.model";
import {Observable, tap, map} from "rxjs";
import {WebApiService} from "../web-api/web-api.service";


export interface Credentials {
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private BASE_URL: string = 'http://localhost:8080';
  user = signal<User | null | undefined>(undefined);

  constructor(private http: WebApiService) {
  }

  login(credentials: Credentials): Observable<User | null | undefined> {
    return this.http.post(this.BASE_URL + '/api/v1/auth/authenticate', credentials).pipe(
      tap((result: any) => {
        console.log(result.body)
        localStorage.setItem('accessToken', result.body.access_token);
        localStorage.setItem('refreshToken', result.body.refresh_token);
      }),
      // map((result: any) => {
      //   return
      // })
    )
  }
}
