import {Component, Signal} from '@angular/core';
import {MatToolbarModule} from "@angular/material/toolbar";
import {PostCardComponent} from "../../components/post-card/post-card.component";
import {MatIcon} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import Post from "../../models/blog/post.model";
import {PostService} from "../../services/blog/posts/post.service";
import {PostCardSkeletonComponent} from "../../loading-skeletons/post-card-skeleton/post-card-skeleton.component";
import {CommonModule} from "@angular/common";
import {toSignal} from "@angular/core/rxjs-interop";
import {map} from "rxjs/operators";
import {NoPostsFoundComponent} from "../../components/no-posts-found/no-posts-found.component";

@Component({
    selector: 'app-home',
    imports: [MatToolbarModule, PostCardComponent, MatIcon, MatButtonModule, RouterLink, CommonModule, PostCardSkeletonComponent, NoPostsFoundComponent],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent {

  posts: Signal<Post[] | [] | null> = toSignal(this.postService.getPosts().pipe(
    map(response => response.body)), {initialValue: []});

  loading:Signal<boolean> = toSignal(this.postService.loadingStatus(), {initialValue:true});

  constructor(private postService: PostService) {
  }
}
