import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ordinalDate',
  standalone: true
})
export class OrdinalDatePipe implements PipeTransform {

  transform(value: Date | string | number): string {
    const date = new Date(value);
    const day = date.getDate();
    const month = date.toLocaleString('default', { month: 'long' });
    const year = date.getFullYear();
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');

    const ordinalSuffix = this.getOrdinalSuffix(day);
    return `${month} ${day}${ordinalSuffix}, ${year}, ${hours}:${minutes}`;
  }

  private getOrdinalSuffix(day: number): string {
    if (day > 3 && day < 21) return 'th';

    switch (day % 10) {
      case 1: return 'st';
      case 2: return 'nd';
      case 3: return 'rd';
      default: return 'th';
    }
  }

}
