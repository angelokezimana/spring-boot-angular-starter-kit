import {Component} from '@angular/core';
import {MatToolbarModule} from "@angular/material/toolbar";
import {PostCardComponent} from "../../../components/post-card/post-card.component";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {RouterModule} from "@angular/router";

@Component({
  selector: 'app-my-posts',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, RouterModule, PostCardComponent],
  templateUrl: './my-posts.component.html',
  styleUrl: './my-posts.component.scss'
})
export class MyPostsComponent {

}
