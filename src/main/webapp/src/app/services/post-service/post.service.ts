import {Injectable} from '@angular/core';
import {WebApiService} from '../web-api/web-api.service';
import {Observable} from 'rxjs';
import Post from '../../models/blog/post.model';
import PostDetail from "../../models/blog/post-detail.model";
import {HttpResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root',
})
export class PostService {
  constructor(private http: WebApiService) {
  }

  getPosts(): Observable<Post | null | undefined> {
    return this.http.get('/api/v1/posts');
  }

  getPostById(id: number): Observable<HttpResponse<PostDetail>> {
    return this.http.get(`/api/v1/posts/${id}`);
  }

  savePost(formData: FormData): Observable<HttpResponse<PostDetail> | null | undefined> {
    return this.http.post('/api/v1/posts', formData, true);
  }
}
