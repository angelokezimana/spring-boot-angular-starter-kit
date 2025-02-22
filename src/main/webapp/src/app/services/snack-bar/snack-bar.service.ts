import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackBarComponent } from '../../components/shared/snack-bar/snack-bar.component';

@Injectable({
  providedIn: 'root'
})
export class SnackBarService {

  constructor(private snackBar: MatSnackBar) {}

  showMessage(message: string, type: 'success' | 'error' | 'info' | 'warning') {
    const icon = this.getIcon(type);
    const color = this.getColor(type);

    this.snackBar.openFromComponent(SnackBarComponent, {
      data: { message, icon },
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition: 'end',
      panelClass: [color],
    });
  }

  private getIcon(type: string): string {
    switch (type) {
      case 'success':
        return 'check_circle';
      case 'error':
        return 'error';
      case 'info':
        return 'info';
      case 'warning':
        return 'warning';
      default:
        return 'notification_important';
    }
  }

  private getColor(type: string): string {
    switch (type) {
      case 'success':
        return 'snackbar-success';
      case 'error':
        return 'snackbar-error';
      case 'info':
        return 'snackbar-info';
      case 'warning':
        return 'snackbar-warning';
      default:
        return 'snackbar-default';
    }
  }
}
