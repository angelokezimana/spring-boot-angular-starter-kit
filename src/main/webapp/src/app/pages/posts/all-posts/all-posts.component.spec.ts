import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllPostsComponent } from './all-posts.component';

describe('AllPostsComponent', () => {
  let component: AllPostsComponent;
  let fixture: ComponentFixture<AllPostsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllPostsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllPostsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
