import { TestBed } from '@angular/core/testing';

import { FormValidationService } from './form-validation.service';

describe('FormFieldServiceService', () => {
  let service: FormValidationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormValidationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
