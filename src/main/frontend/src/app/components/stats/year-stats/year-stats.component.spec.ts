import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { YearStatsComponent } from './year-stats.component';

describe('YearStatsComponent', () => {
  let component: YearStatsComponent;
  let fixture: ComponentFixture<YearStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ YearStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(YearStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
