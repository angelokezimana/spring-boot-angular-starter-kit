import { Component } from '@angular/core';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardFooter,
  MatCardHeader,
  MatCardImage, MatCardTitle
} from "@angular/material/card";
import {MatIconButton} from "@angular/material/button";

@Component({
    selector: 'app-post-card-skeleton',
    imports: [
        MatCard,
        MatCardActions,
        MatCardContent,
        MatCardFooter,
        MatCardHeader,
        MatCardImage,
        MatCardTitle,
        MatIconButton
    ],
    templateUrl: './post-card-skeleton.component.html',
    styleUrl: './post-card-skeleton.component.scss'
})
export class PostCardSkeletonComponent {

}
