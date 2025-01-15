import {Component, OnInit} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatMiniFabAnchor} from "@angular/material/button";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";
import {PostCardComponent} from "../../../components/post-card/post-card.component";
import {RouterLink} from "@angular/router";
import Post from "../../../models/blog/post.model";
import {PostService} from "../../../services/post-service/post.service";

@Component({
  selector: 'app-all-posts',
  standalone: true,
  imports: [
    MatIcon,
    MatMiniFabAnchor,
    MatToolbar,
    MatToolbarRow,
    PostCardComponent,
    RouterLink
  ],
  templateUrl: './all-posts.component.html',
  styleUrl: './all-posts.component.scss'
})
export class AllPostsComponent implements OnInit {
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
