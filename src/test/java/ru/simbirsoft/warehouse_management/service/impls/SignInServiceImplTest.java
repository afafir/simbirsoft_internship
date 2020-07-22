package ru.simbirsoft.warehouse_management.service.impls;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import ru.simbirsoft.warehouse_management.dto.TokenDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignInDto;
import ru.simbirsoft.warehouse_management.model.user.Role;
import ru.simbirsoft.warehouse_management.model.user.User;
import ru.simbirsoft.warehouse_management.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SignInServiceImplTest {

  @InjectMocks private SignInServiceImpl signInService;

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  private User user;
  private SignInDto signInDto;
  private String secret;

  @BeforeEach
  void setUp() {
    secret = "qwerty007";
    ReflectionTestUtils.setField(signInService, "secret", secret);
    user =
        User.builder()
            .email("email")
            .username("username")
            .password("encoded password")
            .id(1L)
            .role(Role.ADMIN)
            .build();
    signInDto = new SignInDto("username", "password");
  }

  @Test
  void signInWhenUsernameDoesntExist() {
    Mockito.doReturn(Optional.empty())
        .when(userRepository)
        .findUserByUsername(signInDto.getUsername());
    assertThrows(AccessDeniedException.class, () -> signInService.signIn(signInDto));
  }

  @Test
  void signInWhenPasswordMismatch() {
    Mockito.doReturn(Optional.of(user))
        .when(userRepository)
        .findUserByUsername(signInDto.getUsername());
    Mockito.doReturn(false)
        .when(passwordEncoder)
        .matches(signInDto.getPassword(), user.getPassword());
    assertThrows(AccessDeniedException.class, () -> signInService.signIn(signInDto));
  }

  @Test
  void signInSuccessfully() {
    Mockito.doReturn(Optional.of(user))
        .when(userRepository)
        .findUserByUsername(signInDto.getUsername());
    Mockito.doReturn(true)
        .when(passwordEncoder)
        .matches(signInDto.getPassword(), user.getPassword());
    TokenDto tokenDto = signInService.signIn(signInDto);
    String token =
        Jwts.builder()
            .setSubject(user.getId().toString()) // id пользователя
            .claim("username", user.getUsername()) // имя
            .claim("role", user.getRole().name()) // роль
            .signWith(SignatureAlgorithm.HS256, secret) // подписываем его с нашим secret
            .compact();
    assertEquals(token, tokenDto.getToken());
  }
}
