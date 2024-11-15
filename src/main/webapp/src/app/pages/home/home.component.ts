import {Component, OnInit} from '@angular/core';
import {MatToolbarModule} from "@angular/material/toolbar";
import {PostCardComponent} from "../../components/post-card/post-card.component";
import {MatIcon} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import Post from "../../models/blog/post.model";
import {PostService} from "../../service/post-service/post.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatToolbarModule, PostCardComponent, MatIcon, MatButtonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  posts: Post[] = [];

  constructor(private postService: PostService) {
  }

  ngOnInit() {
    this.getAllPosts();
  }

  getAllPosts(): void {
    this.postService.getPosts().subscribe((results:any)=>{
      this.posts = results.body;
    })
  }
}
