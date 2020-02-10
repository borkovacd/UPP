import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignedEditorReviewingComponent } from './assigned-editor-reviewing.component';

describe('AssignedEditorReviewingComponent', () => {
  let component: AssignedEditorReviewingComponent;
  let fixture: ComponentFixture<AssignedEditorReviewingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignedEditorReviewingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignedEditorReviewingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
