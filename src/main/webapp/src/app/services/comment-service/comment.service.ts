import {Injectable} from '@angular/core';
import {WebApiService} from "../web-api/web-api.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: WebApiService) {
  }

  getcommentsByPostId(postId: Number): Observable<Comment | null | undefined> {
    return this.http.get(`/api/v1/comments/posts/${postId}`);
  }
}
