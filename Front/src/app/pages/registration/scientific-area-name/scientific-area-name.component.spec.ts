import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientificAreaNameComponent } from './scientific-area-name.component';

describe('ScientificAreaNameComponent', () => {
  let component: ScientificAreaNameComponent;
  let fixture: ComponentFixture<ScientificAreaNameComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScientificAreaNameComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScientificAreaNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
