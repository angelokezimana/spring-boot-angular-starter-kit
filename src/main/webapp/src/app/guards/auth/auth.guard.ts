import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../services/auth/auth.service";

export const authGuard: CanActivateFn = (
  next: ActivatedRouteSnapshot,
  state: RouterStateSnapshot) => {
  const router: Router = inject(Router);
  const authService: AuthService = inject(AuthService);

  if (authService.isAuthenticated()) {
    return true;
  }

  router.navigate(['auth/login']);
  return false;
};
