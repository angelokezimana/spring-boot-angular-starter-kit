import {Component, OnInit, ViewChild} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {HeaderComponent} from "../../components/shared/header/header.component";
import {MatSidenav, MatSidenavModule} from "@angular/material/sidenav";
import {SidebarComponent} from "../../components/shared/sidebar/sidebar.component";
import {BreakpointObserver} from "@angular/cdk/layout";
import {NgClass} from "@angular/common";
import {DefaultFooterComponent} from "../../components/shared/default-footer/default-footer.component";

@Component({
    selector: 'app-default',
    imports: [
        RouterOutlet,
        HeaderComponent,
        DefaultFooterComponent,
        SidebarComponent,
        NgClass,
        MatSidenavModule
    ],
    templateUrl: './default.component.html',
    styleUrl: './default.component.scss'
})
export class DefaultComponent implements OnInit {
  @ViewChild(MatSidenav) sidenav!: MatSidenav;
  isMobile = true;
  isCollapsed = false;

  constructor(private observer: BreakpointObserver) {
  }

  ngOnInit() {
    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      this.isMobile = screenSize.matches;
    });
  }

  toggleMenu() {
    if (this.isMobile) {
      this.sidenav.toggle();
      this.isCollapsed = false;
    } else {
      this.sidenav.open();
      this.isCollapsed = !this.isCollapsed;
    }
  }
}
