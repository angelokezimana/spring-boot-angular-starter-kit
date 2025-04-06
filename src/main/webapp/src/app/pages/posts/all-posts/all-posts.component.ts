import {Component, Signal} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatMiniFabAnchor} from "@angular/material/button";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";
import {PostCardComponent} from "../../../components/post-card/post-card.component";
import {RouterLink} from "@angular/router";
import Post from "../../../models/blog/post.model";
import {PostService} from "../../../services/blog/posts/post.service";
import {toSignal} from "@angular/core/rxjs-interop";
import {map} from "rxjs/operators";

@Component({
    selector: 'app-all-posts',
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
export class AllPostsComponent {
  posts: Signal<Post[] | [] | null> = toSignal(this.postService.getPosts().pipe(
    map(response => response.body)), {initialValue: []});

  constructor(private postService: PostService) {
  }
}
