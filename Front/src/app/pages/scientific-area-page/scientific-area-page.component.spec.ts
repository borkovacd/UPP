import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientificAreaPageComponent } from './scientific-area-page.component';

describe('ScientificAreaPageComponent', () => {
  let component: ScientificAreaPageComponent;
  let fixture: ComponentFixture<ScientificAreaPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScientificAreaPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScientificAreaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
