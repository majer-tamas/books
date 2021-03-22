import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SuccessfullyVerifiedComponent} from './successfully-verified.component';

describe('SuccessfullyVerifiedComponent', () => {
  let component: SuccessfullyVerifiedComponent;
  let fixture: ComponentFixture<SuccessfullyVerifiedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
                                           declarations: [SuccessfullyVerifiedComponent],
                                         })
                 .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SuccessfullyVerifiedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
