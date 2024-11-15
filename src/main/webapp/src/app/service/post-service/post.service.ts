import { Injectable } from '@angular/core';
import {WebApiService} from "../web-api/web-api.service";
import {Observable} from "rxjs";
import Post from "../../models/blog/post.model";


@Injectable({
  providedIn: 'root'
})
export class PostService {

  private BASE_URL = "http://localhost:8080";

  constructor(private http: WebApiService) { }

  getPosts(): Observable<Post | null | undefined> {
    return this.http.get(this.BASE_URL + '/api/v1/posts');
  }
}
