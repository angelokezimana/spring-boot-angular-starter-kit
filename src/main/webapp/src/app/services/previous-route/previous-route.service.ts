import {Injectable} from '@angular/core';
import {Router, NavigationEnd} from "@angular/router";
import {filter} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class PreviousRouteService {

  previousUrl: string = '';
  currentUrl: string = '';

  constructor(private router: Router) {
    this.currentUrl = this.router.url;

    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      this.previousUrl = this.currentUrl;
      this.currentUrl = event.url;
    });
  }
}
