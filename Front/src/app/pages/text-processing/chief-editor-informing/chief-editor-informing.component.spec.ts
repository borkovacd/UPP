import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChiefEditorInformingComponent } from './chief-editor-informing.component';

describe('ChiefEditorInformingComponent', () => {
  let component: ChiefEditorInformingComponent;
  let fixture: ComponentFixture<ChiefEditorInformingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChiefEditorInformingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChiefEditorInformingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
