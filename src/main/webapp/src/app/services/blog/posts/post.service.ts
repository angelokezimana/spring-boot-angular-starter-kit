import {Injectable, signal} from '@angular/core';
import {WebApiService} from '../../web-api/web-api.service';
import {Observable} from 'rxjs';
import Post from '../../../models/blog/post.model';
import PostDetail from "../../../models/blog/post-detail.model";
import {HttpResponse} from "@angular/common/http";
import {tap} from "rxjs/operators";
import {toObservable} from "@angular/core/rxjs-interop";

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private loading = signal(false);

  constructor(private http: WebApiService) {
  }

  getPosts(): Observable<HttpResponse<Post[]>> {
    this.loading.set(true);

    return this.http.get('/api/v1/posts').pipe(
      tap(() => this.loading.set(false))
    );
  }

  getPostById(id: number): Observable<HttpResponse<PostDetail>> {
    return this.http.get(`/api/v1/posts/${id}`);
  }

  savePost(formData: FormData): Observable<HttpResponse<PostDetail> | null | undefined> {
    return this.http.post('/api/v1/posts', formData, true);
  }

  updatePost(formData: FormData, id: number): Observable<any> {
    return this.http.put(`/api/v1/posts/${id}`, formData, true);
  }

  loadingStatus(): Observable<boolean> {
    return toObservable(this.loading);
  }
}
