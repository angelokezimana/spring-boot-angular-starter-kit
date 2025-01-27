import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostCardSkeletonComponent } from './post-card-skeleton.component';

describe('PostCardsComponent', () => {
  let component: PostCardSkeletonComponent;
  let fixture: ComponentFixture<PostCardSkeletonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostCardSkeletonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostCardSkeletonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
