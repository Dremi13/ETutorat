import { TestBed, async, inject } from '@angular/core/testing';

import { NotSigninGuard } from './not-signin.guard';

describe('NotSigninGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NotSigninGuard]
    });
  });

  it('should ...', inject([NotSigninGuard], (guard: NotSigninGuard) => {
    expect(guard).toBeTruthy();
  }));
});
