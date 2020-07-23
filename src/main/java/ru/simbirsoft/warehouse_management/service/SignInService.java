package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.TokenDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignInDto;

public interface SignInService {

  /**
   * Sign in to the system. Retuns jwt token if auth is success
   *
   * @param signInData - form with username and password
   * @return jwt token if auth is successfully
   */
  TokenDto signIn(SignInDto signInData);
}
