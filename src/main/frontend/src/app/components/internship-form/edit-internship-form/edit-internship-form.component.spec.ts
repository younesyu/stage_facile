import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditInternshipFormComponent } from './edit-internship-form.component';

describe('EditInternshipFormComponent', () => {
  let component: EditInternshipFormComponent;
  let fixture: ComponentFixture<EditInternshipFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditInternshipFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditInternshipFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
