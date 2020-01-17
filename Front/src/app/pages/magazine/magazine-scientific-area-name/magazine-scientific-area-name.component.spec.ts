import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazineScientificAreaNameComponent } from './magazine-scientific-area-name.component';

describe('MagazineScientificAreaNameComponent', () => {
  let component: MagazineScientificAreaNameComponent;
  let fixture: ComponentFixture<MagazineScientificAreaNameComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazineScientificAreaNameComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazineScientificAreaNameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
