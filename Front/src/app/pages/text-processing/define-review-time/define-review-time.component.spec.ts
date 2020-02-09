import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DefineReviewTimeComponent } from './define-review-time.component';

describe('DefineReviewTimeComponent', () => {
  let component: DefineReviewTimeComponent;
  let fixture: ComponentFixture<DefineReviewTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DefineReviewTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DefineReviewTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
