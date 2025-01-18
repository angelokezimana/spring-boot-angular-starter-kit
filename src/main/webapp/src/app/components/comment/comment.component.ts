import { Component } from '@angular/core';
import {MatCard, MatCardActions, MatCardContent} from "@angular/material/card";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";

@Component({
  selector: 'app-comment',
  standalone: true,
    imports: [
        MatCard,
        MatCardActions,
        MatCardContent,
        MatIcon,
        MatIconButton
    ],
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.scss'
})
export class CommentComponent {

}
