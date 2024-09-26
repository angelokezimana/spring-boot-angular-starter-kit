import {Component, ViewChild} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {HeaderComponent} from "../../components/header/header.component";
import {MatSidenav, MatSidenavModule} from "@angular/material/sidenav";
import {SidebarComponent} from "../../components/sidebar/sidebar.component";
import {BreakpointObserver} from "@angular/cdk/layout";
import {NgClass, NgIf} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {MatListModule} from "@angular/material/list";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatMenuModule} from "@angular/material/menu";
import {DefaultFooterComponent} from "../../components/default-footer/default-footer.component";
import {MatCardModule} from "@angular/material/card";

@Component({
  selector: 'app-default',
  standalone: true,
  imports: [
    RouterOutlet,
    HeaderComponent,
    DefaultFooterComponent,
    SidebarComponent,
    NgClass,
    NgIf,
    MatSidenavModule,
    MatListModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatCardModule,
  ],
  templateUrl: './default.component.html',
  styleUrl: './default.component.scss'
})
export class DefaultComponent {
  title = 'material-responsive-sidenav';
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;
  isMobile= true;
  isCollapsed = false;


  constructor(private observer: BreakpointObserver) {}

  ngOnInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
        this.isMobile = screenSize.matches;
    });
  }

  toggleMenu() {
    if(this.isMobile){
      this.sidenav.toggle();
      this.isCollapsed = false;
    } else {
      this.sidenav.open();
      this.isCollapsed = !this.isCollapsed;
    }
  }
}
