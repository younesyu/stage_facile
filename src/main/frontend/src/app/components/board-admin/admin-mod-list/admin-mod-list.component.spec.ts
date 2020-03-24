import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminModListComponent } from './admin-mod-list.component';

describe('AdminModListComponent', () => {
  let component: AdminModListComponent;
  let fixture: ComponentFixture<AdminModListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminModListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminModListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
