import {AfterViewInit, Component, OnDestroy, OnInit, Signal, ViewChild} from '@angular/core';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule, PageEvent} from "@angular/material/paginator";
import User from "../../../models/security/user.model";
import {UserService} from "../../../services/admin/users/user.service";
import {MatSort, MatSortModule, Sort} from "@angular/material/sort";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCard, MatCardContent} from "@angular/material/card";
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatMenuModule} from "@angular/material/menu";
import {MatDialog} from "@angular/material/dialog";
import {FormUserComponent} from "./_form/form-user.component";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {debounceTime, Subject, takeUntil} from "rxjs";
import {toSignal} from "@angular/core/rxjs-interop";
import {MatProgressSpinner} from "@angular/material/progress-spinner";

@Component({
  selector: 'app-users',
  imports: [
    MatIconModule,
    MatMenuModule,
    MatToolbar,
    MatToolbarRow,
    MatButtonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatCard,
    FormsModule,
    MatCardContent,
    ReactiveFormsModule,
    MatCheckboxModule,
    MatProgressSpinner,
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent implements OnInit, AfterViewInit, OnDestroy {
  searchControl = new FormControl('');
  private destroy$ = new Subject<void>();

  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'roles', 'actions'];
  dataSource = new MatTableDataSource<User>();

  loading:Signal<boolean> = toSignal(this.userService.loadingStatus(), {initialValue:true});

  totalElements = 0; // To store total number of users for pagination
  pageSize = 10; // Default page size
  pageIndex = 0; // Default page index
  sortBy = 'id'; // Default sort field
  sortOrder = 'desc';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private userService: UserService,
              private liveAnnouncer: LiveAnnouncer,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.searchControl.valueChanges
      .pipe(
        debounceTime(400),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.pageIndex = 0; // Reset to first page on sort
        this.updateDataSource();
      });
  }

  ngAfterViewInit() {
    this.updateDataSource();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  announceSortChange(sortState: Sort) {
    this.sortBy = sortState.active;
    this.sortOrder = sortState.direction || 'asc';
    this.pageIndex = 0; // Reset to first page on sort

    this.updateDataSource();

    if (sortState.direction) {
      this.liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this.liveAnnouncer.announce('Sorting cleared');
    }
  }

  addUser() {
    this.dialog.open(FormUserComponent, {
      width: '800px',
      data: {title: 'Add an user', user: undefined},
    });
  }

  deleteUser(user: User) {

  }

  editUser(user: User) {
    const dialogRef = this.dialog.open(FormUserComponent, {
      width: '800px',
      data: {title: `Edit ${user.firstName} ${user.lastName}`, user: {...user}},
    });

    dialogRef.afterClosed().subscribe((updatedUser) => {
      if (updatedUser) {
        // Update the user in the list
        this.dataSource.data = this.dataSource.data.map((user: User) =>
          user.id === updatedUser.id ? updatedUser : user
        );

        this.liveAnnouncer.announce(`User ${updatedUser.firstName} ${updatedUser.lastName} updated successfully.`);
      }
    });
  }

  getRowNumber(index: number): number {
    if (this.paginator) {
      return this.paginator.pageIndex * this.paginator.pageSize + index + 1;
    }
    return index + 1; // Fallback if paginator is not available
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.updateDataSource();
  }

  private updateDataSource() {
    this.userService.getUsers({
      page: this.pageIndex,
      size: this.pageSize,
      sort: `${this.sortBy},${this.sortOrder}`,
      search: this.searchControl.value as string | undefined
    }).subscribe({
      next: ({body}) => {
        const payload = body!;

        this.dataSource.data = payload.content || [];
        this.totalElements = payload.totalElements || 0;
      },
      error: (error) => {
        console.error('Error fetching users:', error);
        this.dataSource.data = [];
        this.totalElements = 0;
      }
    });
  }
}
