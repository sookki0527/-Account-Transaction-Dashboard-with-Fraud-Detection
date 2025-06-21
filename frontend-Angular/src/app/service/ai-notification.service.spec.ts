import { TestBed } from '@angular/core/testing';

import { AiNotificationService } from './ai-notification.service';

describe('AiNotificationService', () => {
  let service: AiNotificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AiNotificationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
