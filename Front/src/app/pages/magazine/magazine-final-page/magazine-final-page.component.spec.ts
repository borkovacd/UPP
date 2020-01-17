import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazineFinalPageComponent } from './magazine-final-page.component';

describe('MagazineFinalPageComponent', () => {
  let component: MagazineFinalPageComponent;
  let fixture: ComponentFixture<MagazineFinalPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazineFinalPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazineFinalPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
