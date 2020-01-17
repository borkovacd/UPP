import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmReviewerPageComponent } from './confirm-reviewer-page.component';

describe('ConfirmReviewerPageComponent', () => {
  let component: ConfirmReviewerPageComponent;
  let fixture: ComponentFixture<ConfirmReviewerPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmReviewerPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmReviewerPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
