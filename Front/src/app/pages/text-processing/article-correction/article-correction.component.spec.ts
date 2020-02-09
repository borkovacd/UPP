import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleCorrectionComponent } from './article-correction.component';

describe('ArticleCorrectionComponent', () => {
  let component: ArticleCorrectionComponent;
  let fixture: ComponentFixture<ArticleCorrectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticleCorrectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticleCorrectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
