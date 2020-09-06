package ru.simbirsoft.warehouse_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.simbirsoft.warehouse_management.dto.TokenDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignInDto;
import ru.simbirsoft.warehouse_management.model.user.Role;
import ru.simbirsoft.warehouse_management.service.SignInService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SignInControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean
  SignInService signInService;

  private SignInDto signInDto;
  private TokenDto token;
  @BeforeEach
  void setUp() {
    signInDto = new SignInDto("username", "password");
    String jwtToken =
            Jwts.builder()
                    .setSubject(String.valueOf(1L)) // id пользователя
                    .claim("username", signInDto.getUsername()) // имя
                    .claim("role", Role.ADMIN.name()) // роль
                    .signWith(SignatureAlgorithm.HS256, "qwerty007") // подписываем его с нашим secret
                    .compact();
    token = new TokenDto(jwtToken);
  }

  @Test
  void signInWithEmptyField() throws Exception {
      signInDto.setPassword("");
         mockMvc
            .perform(
                    post("/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInDto)))
            .andExpect(status().isBadRequest());

  }

  @Test
  void signInWithNullField() throws Exception {
    signInDto.setPassword(null);
    mockMvc
            .perform(
                    post("/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInDto)))
            .andExpect(status().isBadRequest());

  }

  @Test
  void signInWithIncorrectData() throws Exception {
    given(signInService.signIn(signInDto)).willThrow(new AccessDeniedException("wrong username or password"));
    mockMvc
            .perform(
                    post("/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInDto)))
            .andExpect(status().isForbidden());

  }

  @Test
  void signInSuccessfully() throws Exception {
    given(signInService.signIn(signInDto)).willReturn(token);
    MvcResult result = mockMvc
            .perform(
                    post("/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInDto)))
            .andExpect(status().isOk()).andReturn();
    JSONAssert.assertEquals(objectMapper.writeValueAsString(token), result.getResponse().getContentAsString(), false);


  }
}