import { TestBed } from '@angular/core/testing';

import { TextProcessingService } from './text-processing.service';

describe('TextProcessingService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TextProcessingService = TestBed.get(TextProcessingService);
    expect(service).toBeTruthy();
  });
});
