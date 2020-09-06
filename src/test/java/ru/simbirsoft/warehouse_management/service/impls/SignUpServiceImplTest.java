package ru.simbirsoft.warehouse_management.service.impls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.simbirsoft.warehouse_management.dto.UserDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignUpDto;
import ru.simbirsoft.warehouse_management.dto.mapper.UserMapper;
import ru.simbirsoft.warehouse_management.dto.mapper.UserMapperImpl;
import ru.simbirsoft.warehouse_management.exception.UserAlreadyExistException;
import ru.simbirsoft.warehouse_management.model.user.Role;
import ru.simbirsoft.warehouse_management.model.user.User;
import ru.simbirsoft.warehouse_management.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SignUpServiceImplTest {

  @InjectMocks private SignUpServiceImpl signUpService;

  @Mock private UserRepository userRepository;
  @Mock private BCryptPasswordEncoder passwordEncoder;
  @Mock private UserMapper userMapper;

  private SignUpDto signUpDto;
  private User toSave;
  private User saved;
  private UserDto userDto;

  @BeforeEach
  void setUp() {
    UserMapper userMapper = new UserMapperImpl();
    signUpDto =
        SignUpDto.builder()
            .email("qwerty@qwerty.ru")
            .password("qwerty007")
            .role(Role.ADMIN)
            .username("superusername")
            .build();
    toSave =
        User.builder()
            .password("encoded password")
            .username(signUpDto.getUsername())
            .email(signUpDto.getEmail())
            .role(signUpDto.getRole())
            .build();
    saved =
        User.builder()
            .id(1L)
            .password("encoded password")
            .username(signUpDto.getUsername())
            .email(signUpDto.getEmail())
            .role(signUpDto.getRole())
            .build();
    userDto = userMapper.mapToDto(toSave);
    }

  @Test
  void signUpWhenUsernameExists() {
    Mockito.doReturn(true).when(userRepository).existsByUsername(signUpDto.getUsername());
    assertThrows(UserAlreadyExistException.class, ()->signUpService.signUp(signUpDto));
  }

  @Test
  void signUpWhenEmailExists() {
    Mockito.doReturn(false).when(userRepository).existsByUsername(signUpDto.getUsername());
    Mockito.doReturn(true).when(userRepository).existsByEmail(signUpDto.getEmail());
    assertThrows(UserAlreadyExistException.class, ()->signUpService.signUp(signUpDto));
  }

  @Test
  void signUp(){
    Mockito.doReturn(false).when(userRepository).existsByUsername(signUpDto.getUsername());
    Mockito.doReturn(false).when(userRepository).existsByEmail(signUpDto.getEmail());
    Mockito.doReturn("encoded password").when(passwordEncoder).encode(signUpDto.getPassword());
    Mockito.doReturn(saved).when(userRepository).save(toSave);
    Mockito.doReturn(userDto).when(userMapper).mapToDto(saved);
    UserDto result = signUpService.signUp(signUpDto);
    assertEquals(userDto.getEmail(), result.getEmail());
    assertEquals(userDto.getId(), result.getId());
    assertEquals(userDto.getUsername(), result.getUsername());

  }

}
