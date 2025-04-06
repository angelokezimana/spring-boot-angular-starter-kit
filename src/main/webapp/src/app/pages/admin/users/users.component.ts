import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbar, MatToolbarRow} from "@angular/material/toolbar";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import User from "../../../models/security/user.model";
import {UserService} from "../../../services/admin/users/user.service";
import {MatSort, MatSortModule, Sort} from "@angular/material/sort";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCard, MatCardContent} from "@angular/material/card";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatMenuModule} from "@angular/material/menu";
import {MatDialog} from "@angular/material/dialog";
import {FormUserComponent} from "./_form/form-user.component";
import {MatCheckboxModule} from "@angular/material/checkbox";

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
        MatCheckboxModule
    ],
    templateUrl: './users.component.html',
    styleUrl: './users.component.scss'
})
export class UsersComponent implements AfterViewInit {
  search = '';
  displayedColumns: string[] = ['selected', 'id', 'firstName', 'lastName', 'email', 'roles', 'actions'];
  dataSource = new MatTableDataSource<User>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private userService: UserService,
              private liveAnnouncer: LiveAnnouncer,
              private dialog: MatDialog) {
  }

  ngAfterViewInit() {
    this.updateDataSource();
  }

  announceSortChange(sortState: Sort) {
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

  private updateDataSource() {
    this.userService.getUsers().subscribe({
      next: data => {
        console.log(data?.body);

        if (data.body) {
          this.dataSource.data = data.body;
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        }
      }
    });
  }
}
