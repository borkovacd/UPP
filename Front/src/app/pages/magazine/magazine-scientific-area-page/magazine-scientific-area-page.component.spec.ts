import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazineScientificAreaPageComponent } from './magazine-scientific-area-page.component';

describe('MagazineScientificAreaPageComponent', () => {
  let component: MagazineScientificAreaPageComponent;
  let fixture: ComponentFixture<MagazineScientificAreaPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazineScientificAreaPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazineScientificAreaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
