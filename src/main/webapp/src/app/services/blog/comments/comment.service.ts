import {Injectable} from '@angular/core';
import {WebApiService} from "../../web-api/web-api.service";
import {Observable} from "rxjs";
import Comment from "../../../models/blog/comment.model";
import {HttpResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: WebApiService) {
  }

  getCommentsByPostId(postId: number): Observable<HttpResponse<Comment[]>> {
    return this.http.get(`/api/v1/comments/posts/${postId}`);
  }

  saveComment(postId:number, text: String):Observable<HttpResponse<Comment>> {
      return this.http.post(`/api/v1/comments/posts/${postId}`, text);
  }
}
