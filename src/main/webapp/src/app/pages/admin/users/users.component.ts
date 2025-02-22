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

@Component({
  selector: 'app-users',
  standalone: true,
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
  ],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss'
})
export class UsersComponent implements AfterViewInit {
  search = '';
  displayedColumns: string[] = ['firstName', 'lastName', 'email', 'roles', 'actions'];
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
      data: {title: 'Add an user', user: undefined},
    });
  }

  deleteUser(user: User) {

  }

  editUser(user: User) {
    this.dialog.open(FormUserComponent, {
      data: {title: `Edit ${user.firstName} ${user.lastName}`, user: user},
    });
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
