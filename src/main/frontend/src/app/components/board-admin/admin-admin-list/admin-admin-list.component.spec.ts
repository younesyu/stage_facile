import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAdminListComponent } from './admin-admin-list.component';

describe('AdminAdminListComponent', () => {
  let component: AdminAdminListComponent;
  let fixture: ComponentFixture<AdminAdminListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminAdminListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAdminListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
