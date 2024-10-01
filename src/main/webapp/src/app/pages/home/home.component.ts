import { Component } from '@angular/core';
import {MatCardModule} from "@angular/material/card";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {TruncatePipe} from "../../pipe/truncate.pipe";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatCardModule, MatToolbarModule, MatButtonModule, TruncatePipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
