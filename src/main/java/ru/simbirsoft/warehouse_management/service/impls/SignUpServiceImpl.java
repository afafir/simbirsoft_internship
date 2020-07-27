package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.UserDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignUpDto;
import ru.simbirsoft.warehouse_management.dto.mapper.UserMapper;
import ru.simbirsoft.warehouse_management.exception.UserAlreadyExistException;
import ru.simbirsoft.warehouse_management.model.user.User;
import ru.simbirsoft.warehouse_management.repository.UserRepository;
import ru.simbirsoft.warehouse_management.service.SignUpService;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final UserMapper userMapper;

  @Override
  public UserDto signUp(SignUpDto signUpData) {
    if (userRepository.existsByUsername(signUpData.getUsername())) {
      throw new UserAlreadyExistException("User with this username already exists");
    }
    if (userRepository.existsByEmail(signUpData.getEmail())) {
      throw new UserAlreadyExistException("User with this email already exists");
    }
    User user =
        User.builder()
            .email(signUpData.getEmail())
            .username(signUpData.getUsername())
            .password(passwordEncoder.encode(signUpData.getPassword()))
            .role(signUpData.getRole())
            .build();
    return userMapper.mapToDto(userRepository.save(user));
  }
}
