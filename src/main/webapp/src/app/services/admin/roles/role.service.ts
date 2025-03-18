import {Injectable, signal} from '@angular/core';
import {WebApiService} from "../../web-api/web-api.service";
import {Observable} from "rxjs";
import {HttpResponse} from "@angular/common/http";
import {tap} from "rxjs/operators";
import Role from "../../../models/security/role.model";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private loading = signal(false);

  constructor(private http: WebApiService) { }

  getAllRoles(): Observable<HttpResponse<Role[]>> {
    this.loading.set(true);

    return this.http.get('/api/v1/admin/roles/all').pipe(
      tap(() => this.loading.set(false))
    );
  }
}
