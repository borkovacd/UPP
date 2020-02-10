import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleCorrectionAfterReviewComponent } from './article-correction-after-review.component';

describe('ArticleCorrectionAfterReviewComponent', () => {
  let component: ArticleCorrectionAfterReviewComponent;
  let fixture: ComponentFixture<ArticleCorrectionAfterReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticleCorrectionAfterReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticleCorrectionAfterReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
