import {Component, input} from '@angular/core';
import {AsyncPipe, NgClass} from '@angular/common';

import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {Event, NavigationEnd, Router, RouterModule} from "@angular/router";
import {filter, map} from "rxjs/operators";
import {Observable} from "rxjs";

import MenuItem from "../../../interface/MenuItem";


@Component({
    selector: 'app-sidebar',
    imports: [MatSidenavModule, MatIconModule, MatListModule, NgClass, RouterModule, AsyncPipe],
    templateUrl: './sidebar.component.html',
    styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  isCollapsed = input<boolean>(false);
  openSubMenus: { [key: number]: boolean } = {};
  currentUrl$: Observable<string>;

  menuItems: MenuItem[] = [
    {
      name: 'Dashboard',
      link: '/home',
      icon: 'house',
      submenu: []
    },
    {
      name: 'Posts',
      link: '',
      icon: 'newspaper',
      submenu: [
        {name: 'All posts', link: '/all-posts', icon: 'grid_view', submenu: []},
        {name: 'My posts', link: '/my-posts', icon: 'article', submenu: []},
        {name: 'Add post', link: '/post-form', icon: 'add', submenu: []},
      ]
    },
    {
      name: 'Admin',
      link: '',
      icon: 'admin_panel_settings',
      submenu: [
        {name: 'Roles', link: '/admin/roles', icon: 'group', submenu: []},
        {name: 'Users', link: '/admin/users', icon: 'person', submenu: []}
      ]
    }
  ];

  constructor(private router: Router) {
    this.currentUrl$ = this.router.events.pipe(
      filter((event: Event): event is NavigationEnd => event instanceof NavigationEnd),
      map((event: NavigationEnd) => event.urlAfterRedirects)
    );
  }

  toggleSubMenu(index: number): void {
    this.openSubMenus[index] = !this.openSubMenus[index];
  }

  isSubMenuOpen(index: number): boolean {
    return this.openSubMenus[index] || false;
  }

  isMenuItemActive(currentUrl: string|null, item: MenuItem): boolean|undefined {
    return currentUrl?.includes(item.link);
  }
}
