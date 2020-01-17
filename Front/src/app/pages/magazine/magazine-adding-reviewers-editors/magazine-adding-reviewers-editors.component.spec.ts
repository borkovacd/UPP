import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazineAddingReviewersEditorsComponent } from './magazine-adding-reviewers-editors.component';

describe('MagazineAddingReviewersEditorsComponent', () => {
  let component: MagazineAddingReviewersEditorsComponent;
  let fixture: ComponentFixture<MagazineAddingReviewersEditorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazineAddingReviewersEditorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazineAddingReviewersEditorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
