import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignedEditorReviewingCorrectedComponent } from './assigned-editor-reviewing-corrected.component';

describe('AssignedEditorReviewingCorrectedComponent', () => {
  let component: AssignedEditorReviewingCorrectedComponent;
  let fixture: ComponentFixture<AssignedEditorReviewingCorrectedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignedEditorReviewingCorrectedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignedEditorReviewingCorrectedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
