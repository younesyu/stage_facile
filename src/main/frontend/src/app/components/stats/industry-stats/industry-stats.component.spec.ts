import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndustryStatsComponent } from './industry-stats.component';

describe('IndustryStatsComponent', () => {
  let component: IndustryStatsComponent;
  let fixture: ComponentFixture<IndustryStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndustryStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndustryStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
