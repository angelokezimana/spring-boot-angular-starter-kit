import { Component, Inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';

@Component({
    selector: 'app-snack-bar',
    imports: [MatIconModule],
    templateUrl: './snack-bar.component.html',
    styleUrl: './snack-bar.component.scss'
})
export class SnackBarComponent {
  constructor(
    @Inject(MAT_SNACK_BAR_DATA) public data: { message: string; icon: string }
  ) {}
}
