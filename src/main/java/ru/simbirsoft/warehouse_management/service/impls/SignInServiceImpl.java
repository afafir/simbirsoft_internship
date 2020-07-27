package ru.simbirsoft.warehouse_management.service.impls;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.TokenDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignInDto;
import ru.simbirsoft.warehouse_management.model.user.User;
import ru.simbirsoft.warehouse_management.repository.UserRepository;
import ru.simbirsoft.warehouse_management.service.SignInService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Value("${jwt.secret}")
  private String secret;

  @Override
  public TokenDto signIn(SignInDto signInData) {
    // получаем пользователя по его email
    Optional<User> userOptional = userRepository.findUserByUsername(signInData.getUsername());
    // если у меня есть этот пользвователь
    if (userOptional.isPresent()) {
      // получаем его
      User user = userOptional.get();
      // если пароль подходит
      if (passwordEncoder.matches(signInData.getPassword(), user.getPassword())) {
        // создаем токен
        String token =
            Jwts.builder()
                .setSubject(user.getId().toString()) // id пользователя
                .claim("username", user.getUsername()) // имя
                .claim("role", user.getRole().name()) // роль
                .signWith(SignatureAlgorithm.HS256, secret) // подписываем его с нашим secret
                .compact(); // преобразовали в строку
        return new TokenDto(token);
      } else throw new AccessDeniedException("Wrong email/password");
    } else throw new AccessDeniedException("User not found");
  }
}
