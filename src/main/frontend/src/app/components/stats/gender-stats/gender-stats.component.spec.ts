import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenderStatsComponent } from './gender-stats.component';

describe('GenderStatsComponent', () => {
  let component: GenderStatsComponent;
  let fixture: ComponentFixture<GenderStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenderStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenderStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
