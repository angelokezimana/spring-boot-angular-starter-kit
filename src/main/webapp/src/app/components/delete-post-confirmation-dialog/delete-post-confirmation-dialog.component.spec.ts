import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletePostConfirmationDialogComponent } from './delete-post-confirmation-dialog.component';

describe('DeleteComponent', () => {
  let component: DeletePostConfirmationDialogComponent;
  let fixture: ComponentFixture<DeletePostConfirmationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeletePostConfirmationDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeletePostConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
