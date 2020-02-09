import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChoosingReviewersComponent } from './choosing-reviewers.component';

describe('ChoosingReviewersComponent', () => {
  let component: ChoosingReviewersComponent;
  let fixture: ComponentFixture<ChoosingReviewersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChoosingReviewersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChoosingReviewersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
