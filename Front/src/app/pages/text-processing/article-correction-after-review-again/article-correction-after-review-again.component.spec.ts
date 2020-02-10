import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleCorrectionAfterReviewAgainComponent } from './article-correction-after-review-again.component';

describe('ArticleCorrectionAfterReviewAgainComponent', () => {
  let component: ArticleCorrectionAfterReviewAgainComponent;
  let fixture: ComponentFixture<ArticleCorrectionAfterReviewAgainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticleCorrectionAfterReviewAgainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticleCorrectionAfterReviewAgainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
