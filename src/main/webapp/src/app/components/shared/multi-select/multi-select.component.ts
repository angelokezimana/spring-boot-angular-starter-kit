import {
  Component,
  computed,
  input,
  OnChanges,
  output,
  Signal,
  signal,
  SimpleChanges,
  WritableSignal
} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatChipsModule} from "@angular/material/chips";
import {MatAutocompleteModule, MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {toSignal} from "@angular/core/rxjs-interop";

@Component({
    selector: 'app-multi-select',
    imports: [ReactiveFormsModule, MatInputModule, MatIconModule, MatChipsModule, MatAutocompleteModule],
    templateUrl: './multi-select.component.html',
    styleUrl: './multi-select.component.scss'
})
export class MultiSelectComponent<T> implements OnChanges {

  allItems = input<WritableSignal<T[]>>(signal<T[]>([]));
  initialSelectedItems = input<T[]>([]);
  displayProperty = input<keyof T>('name' as keyof T);
  label = input<string>('Select Items');
  placeholder = input<string>('Add Item...');

  selectedItemsChange = output<T[]>();

  currentItem = new FormControl<T | null>(null);
  private readonly currentItemValue: Signal<T | null> = toSignal(this.currentItem.valueChanges, {initialValue: null});

  readonly selectedItems: WritableSignal<T[]> = signal<T[]>([]);

  readonly filteredItems: Signal<T[]> = computed((): T[] => {
    const currentValue: T | null = this.currentItemValue();
    const selected: T[] = this.selectedItems();
    const available: T[] = this.allItems()();

    const itemsNotSelected: T[] = available.filter(
      (item: T): boolean => !selected.some((selectedItem: T): boolean => this.getId(selectedItem) === this.getId(item))
    );

    if (!currentValue || !currentValue[this.displayProperty()]) {
      return itemsNotSelected;
    }

    const filterText: string = String(currentValue[this.displayProperty()]).toLowerCase();
    return itemsNotSelected.filter((item: T): boolean =>
      String(item[this.displayProperty()]).toLowerCase().includes(filterText)
    );
  });

  constructor(private announcer: LiveAnnouncer) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['initialSelectedItems'] && this.initialSelectedItems().length > 0) {
      this.selectedItems.set(this.initialSelectedItems());
      this.allItems().update((allItems: T[]): T[] =>
        allItems.filter((item: T): boolean =>
          !this.initialSelectedItems().some((selected: T): boolean => this.getId(selected) === this.getId(item)))
      );
      this.selectedItemsChange.emit(this.selectedItems());
    }
  }

  remove(item: T): void {
    this.selectedItems.update((items: T[]): T[] => items.filter((i: T): boolean => this.getId(i) !== this.getId(item)));
    this.announcer.announce(`Removed ${String(item[this.displayProperty()])}`);
    this.allItems().update((allItems: T[]): T[] => {
      if (!allItems.some((i: T): boolean => this.getId(i) === this.getId(item))) {
        return [...allItems, item];
      }
      return allItems;
    });
    this.selectedItemsChange.emit(this.selectedItems());
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const selectedItem = event.option.value as T;
    this.selectedItems.update((items: T[]): T[] => {
      if (!items.some((i: T): boolean => this.getId(i) === this.getId(selectedItem))) {
        return [...items, selectedItem];
      }
      return items;
    });
    this.allItems().update((allItems: T[]): T[] => allItems.filter((i: T): boolean => this.getId(i) !== this.getId(selectedItem)));
    this.currentItem.reset(null);
    event.option.deselect();
    this.selectedItemsChange.emit(this.selectedItems());
  }

  onInput(event: Event): void {
    const inputValue: string = (event.target as HTMLInputElement).value;
    if (inputValue) {
      const partialItem = {[this.displayProperty()]: inputValue} as T;
      this.currentItem.setValue(partialItem);
    } else {
      this.currentItem.setValue(null);
    }
  }

  getId(item: T): any {
    return (item as any).id; // Adjust if your items have a different ID property
  }
}
