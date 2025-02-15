import {Injectable, signal} from '@angular/core';
import {WebApiService} from "../../web-api/web-api.service";
import {Observable} from "rxjs";
import {HttpResponse} from "@angular/common/http";
import {tap} from "rxjs/operators";
import {toObservable} from "@angular/core/rxjs-interop";
import User from "../../../models/security/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private loading = signal(false);

  constructor(private http: WebApiService) { }

  getUsers(): Observable<HttpResponse<User[]>> {
    this.loading.set(true);

    return this.http.get('/api/v1/admin/users').pipe(
      tap(() => this.loading.set(false))
    );
  }

  getUserById(id: number): Observable<HttpResponse<User>> {
    return this.http.get(`/api/v1/admin/users/${id}`);
  }

  saveUser(user: User): Observable<HttpResponse<User> | null | undefined> {
    return this.http.post('/api/v1/admin/users', user);
  }

  updateUser(user: User, id: number): Observable<any> {
    return this.http.put(`/api/v1/admin/users/${id}`, user);
  }

  loadingStatus(): Observable<boolean> {
    return toObservable(this.loading);
  }
}
