package ru.simbirsoft.warehouse_management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.warehouse_management.dto.UserDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignUpDto;
import ru.simbirsoft.warehouse_management.service.SignUpService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api("Sign up controller for users")
public class SignUpController {

  private final SignUpService signUpService;

  @ApiOperation(
      value =
          "Creates new user. If operation is successfully then returns created user with id. " +
                  "Otherwise, returns custom error message  ")
  @PostMapping("${url.signup}")
  private UserDto signUp(@RequestBody @Valid SignUpDto signUpDto) {
    return signUpService.signUp(signUpDto);
  }
}
