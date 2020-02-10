import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewDoneComponent } from './review-done.component';

describe('ReviewDoneComponent', () => {
  let component: ReviewDoneComponent;
  let fixture: ComponentFixture<ReviewDoneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewDoneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewDoneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
