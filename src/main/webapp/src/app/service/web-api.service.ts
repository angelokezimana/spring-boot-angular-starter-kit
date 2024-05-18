import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class WebApiService {

  constructor(private http: HttpClient) { }

  /**
   * GET
   */
  get(url: string): Observable<any> {
    const httpOtions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Cache-Control' : 'no-cache',
        'Pragma' : 'no-cache'
      }),
      observe: "response" as 'body'
    };

    return this.http.get(
      url,
      httpOtions
    ).pipe(map((response:any) => response), catchError(throwError))
  }

  /**
   * POST
   */
  post(url: string, model: any): Observable<any> {
    const httpOtions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      }),
      observe: "response" as 'body'
    };

    return this.http.post(
      url,
      model,
      httpOtions
    ).pipe(map((response: any) => response), catchError(throwError));
  }

  /**
   * PUT
   */
  put(url: string, model: any): Observable<any> {
    const httpOtions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      }),
      observe: "response" as 'body'
    };

    return this.http.put(
      url,
      model,
      httpOtions
    ).pipe(map((response: any) => response), catchError(throwError));
  }

  /**
   * DELETE
   */
  delete(url: string, model: any): Observable<any> {
    return this.http.delete(
      url,
      model
    ).pipe(map((response: any) => response), catchError(throwError));
  }
}
