package ru.simbirsoft.warehouse_management.service.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.warehouse_management.dto.UserDto;
import ru.simbirsoft.warehouse_management.dto.mapper.UserMapper;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;
import ru.simbirsoft.warehouse_management.model.user.User;
import ru.simbirsoft.warehouse_management.repository.UserRepository;
import ru.simbirsoft.warehouse_management.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    //repository
    private final UserRepository userRepository;

    //mappers
    private final UserMapper userMapper;


    @Override
    public UserDto getById(Long id) {
        if (userRepository.existsById(id)){
            return userMapper.mapToDto(userRepository.getOne(id));
        } else throw new NotFoundException(User.class, "id", String.valueOf(id));
    }
}
