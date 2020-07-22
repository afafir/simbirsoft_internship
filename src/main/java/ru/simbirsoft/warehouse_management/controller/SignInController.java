package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.warehouse_management.dto.TokenDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignInDto;
import ru.simbirsoft.warehouse_management.service.SignInService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api("Sign up controller for users (uses jwt)")
public class SignInController {

  private final SignInService signInService;

  @ApiOperation(
      value =
          "Sign in to the application. If authentication is successfully then returns jwt token. "
              + "Otherwise, returns custom error message  ")
  @PostMapping("${url.signin}")
  public ResponseEntity<TokenDto> signIn(@RequestBody @Valid SignInDto signInData) {
    return ResponseEntity.ok(signInService.signIn(signInData));
  }
}
