import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdfReviewComponent } from './pdf-review.component';

describe('PdfReviewComponent', () => {
  let component: PdfReviewComponent;
  let fixture: ComponentFixture<PdfReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdfReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdfReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
