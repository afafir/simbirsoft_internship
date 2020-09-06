package ru.simbirsoft.warehouse_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.simbirsoft.warehouse_management.dto.UserDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignUpDto;
import ru.simbirsoft.warehouse_management.exception.UserAlreadyExistException;
import ru.simbirsoft.warehouse_management.model.user.Role;
import ru.simbirsoft.warehouse_management.service.SignInService;
import ru.simbirsoft.warehouse_management.service.SignUpService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {

  @Autowired MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean SignUpService signUpService;

  private SignUpDto signUpDto;
  private UserDto userDto;

  @BeforeEach
  void setUp() {
    signUpDto =
        SignUpDto.builder()
            .username("username")
            .role(Role.ADMIN)
            .password("qwerty007")
            .email("email@email.ru")
            .build();
    userDto =
        UserDto.builder()
            .email(signUpDto.getEmail())
            .id(1L)
            .username(signUpDto.getUsername())
            .role(signUpDto.getRole())
            .build();
  }

  @Test
  void signUpWithEmptyField() throws Exception {
    signUpDto.setPassword("");
    mockMvc
        .perform(
            post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void signUpWithNullField() throws Exception {
    signUpDto.setPassword(null);
    mockMvc
        .perform(
            post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void signUpWithExistingUsernameOrEmail() throws Exception {
    given(signUpService.signUp(signUpDto))
        .willThrow(new UserAlreadyExistException("user already exists"));
    mockMvc
        .perform(
            post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDto)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void signUpSuccessfully() throws Exception {
    given(signUpService.signUp(signUpDto)).willReturn(userDto);
    MvcResult result =
        mockMvc
            .perform(
                post("/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpDto)))
            .andExpect(status().isOk())
            .andReturn();
    JSONAssert.assertEquals(
        objectMapper.writeValueAsString(userDto), result.getResponse().getContentAsString(), false);
  }
}
