import { Component } from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatMiniFabAnchor} from "@angular/material/button";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";
import {PostCardComponent} from "../../../components/post-card/post-card.component";
import {RouterLink} from "@angular/router";

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
export class AllPostsComponent {

}
