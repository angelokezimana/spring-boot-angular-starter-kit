import {Injectable, signal} from '@angular/core';
import {WebApiService} from "../../web-api/web-api.service";
import {Observable} from "rxjs";
import {HttpResponse} from "@angular/common/http";
import {tap} from "rxjs/operators";
import {toObservable} from "@angular/core/rxjs-interop";
import User from "../../../models/security/user.model";
import UserRequest from "../../../models/security/user.request.model";
import {PagedResponse} from "../../../interface/PagedResponse";
import {QueryParams} from "../../../interface/QueryParams";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private loading = signal(false);

  constructor(private http: WebApiService) { }

  getUsers(params: QueryParams): Observable<HttpResponse<PagedResponse<User>>> {
    this.loading.set(true);

    return this.http.get('/api/v1/admin/users', params).pipe(
      tap(() => this.loading.set(false))
    );
  }

  getUserById(id: number): Observable<HttpResponse<User>> {
    return this.http.get(`/api/v1/admin/users/${id}`);
  }

  saveUser(user: UserRequest): Observable<HttpResponse<User> | null | undefined> {
    return this.http.post('/api/v1/admin/users', user);
  }

  updateUser(user: User, id: number): Observable<any> {
    return this.http.put(`/api/v1/admin/users/${id}`, user);
  }

  loadingStatus(): Observable<boolean> {
    return toObservable(this.loading);
  }
}
