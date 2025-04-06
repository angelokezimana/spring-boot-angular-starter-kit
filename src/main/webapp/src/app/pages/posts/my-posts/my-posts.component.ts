import {Component, OnInit} from '@angular/core';
import {MatToolbarModule} from "@angular/material/toolbar";
import {PostCardComponent} from "../../../components/post-card/post-card.component";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {RouterModule} from "@angular/router";
import Post from "../../../models/blog/post.model";
import {PostService} from "../../../services/blog/posts/post.service";

@Component({
    selector: 'app-my-posts',
    imports: [MatToolbarModule, MatButtonModule, MatIconModule, RouterModule, PostCardComponent],
    templateUrl: './my-posts.component.html',
    styleUrl: './my-posts.component.scss'
})
export class MyPostsComponent  implements OnInit {
  posts: Post[] = [];

  constructor(private postService: PostService) {
  }

  ngOnInit() {
    this.getAllPosts();
  }

  getAllPosts(): void {
    this.postService.getPosts().subscribe((results: any) => {
      this.posts = results.body;
    })
  }
}
