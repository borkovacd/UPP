import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoauthorDataComponent } from './coauthor-data.component';

describe('CoauthorDataComponent', () => {
  let component: CoauthorDataComponent;
  let fixture: ComponentFixture<CoauthorDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoauthorDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoauthorDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
