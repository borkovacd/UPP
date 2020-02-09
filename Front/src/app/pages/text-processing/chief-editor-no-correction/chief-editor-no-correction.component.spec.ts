import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChiefEditorNoCorrectionComponent } from './chief-editor-no-correction.component';

describe('ChiefEditorNoCorrectionComponent', () => {
  let component: ChiefEditorNoCorrectionComponent;
  let fixture: ComponentFixture<ChiefEditorNoCorrectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChiefEditorNoCorrectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChiefEditorNoCorrectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
