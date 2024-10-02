import {Component} from '@angular/core';
import {MatToolbarModule} from "@angular/material/toolbar";
import {PostCardComponent} from "../../components/post-card/post-card.component";
import {MatIcon} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatToolbarModule, PostCardComponent, MatIcon, MatButtonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
