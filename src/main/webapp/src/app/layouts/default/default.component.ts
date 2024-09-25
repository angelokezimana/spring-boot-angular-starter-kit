import {Component} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {HeaderComponent} from "../../components/header/header.component";
import {MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {SidebarComponent} from "../../components/sidebar/sidebar.component";
import {BreakpointObserver} from "@angular/cdk/layout";

@Component({
  selector: 'app-default',
  standalone: true,
  imports: [
    RouterOutlet,
    HeaderComponent,
    MatSidenavContainer,
    MatSidenavContent,
    SidebarComponent
  ],
  templateUrl: './default.component.html',
  styleUrl: './default.component.css'
})
export class DefaultComponent {
  isMobile = false;
  isCollapsed = true;

  constructor(private observer: BreakpointObserver) {
  }

  ngOnInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      this.isMobile = screenSize.matches;
    });
  }

  onToggleMenu() {
    this.toggleMenu();
  }

  toggleMenu() {
    if (this.isMobile) {
      this.isCollapsed = false;
    } else {
      this.isCollapsed = !this.isCollapsed;
    }
  }
}
