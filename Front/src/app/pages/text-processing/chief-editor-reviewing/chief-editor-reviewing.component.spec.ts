import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChiefEditorReviewingComponent } from './chief-editor-reviewing.component';

describe('ChiefEditorReviewingComponent', () => {
  let component: ChiefEditorReviewingComponent;
  let fixture: ComponentFixture<ChiefEditorReviewingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChiefEditorReviewingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChiefEditorReviewingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
