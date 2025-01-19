import {Component, OnInit} from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {PostService} from "../../../services/post-service/post.service";
import {HttpResponse} from "@angular/common/http";
import PostDetail from "../../../models/blog/post-detail.model";
import {DatePipe} from "@angular/common";
import {PreviousRouteService} from "../../../services/previous-route/previous-route.service";

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [MatCardModule, MatIconModule, MatButtonModule, MatFormFieldModule, MatInputModule, DatePipe, RouterLink],
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.scss'
})
export class PostDetailComponent implements OnInit {
  post: PostDetail | null = null;
  previousUrl: string = '';

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private previousRouteService: PreviousRouteService) {
  }

  ngOnInit() {
    const postId = this.route.snapshot.paramMap.get('id');
    this.previousUrl = this.previousRouteService.previousUrl;

    if (postId) {
      this.postService.getPostById(+postId).subscribe({
        next: (response: HttpResponse<PostDetail>) => {
          this.post = response.body;
        },
        error: (err) => {
          console.error(err);
        },
      });
    }
  }

  deletePost(): void {

  }
}
