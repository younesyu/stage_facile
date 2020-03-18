import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InternshipListComponent } from './internship-list.component';

describe('InternshipListComponent', () => {
  let component: InternshipListComponent;
  let fixture: ComponentFixture<InternshipListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InternshipListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InternshipListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
