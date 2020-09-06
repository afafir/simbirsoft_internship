package ru.simbirsoft.warehouse_management.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.simbirsoft.warehouse_management.dto.CategoryDto;
import ru.simbirsoft.warehouse_management.dto.UserDto;
import ru.simbirsoft.warehouse_management.model.Category;
import ru.simbirsoft.warehouse_management.model.user.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
  User map(UserDto user);

  List<User> mapList(List<UserDto> users);

  @InheritInverseConfiguration
  UserDto mapToDto(User user);

  @InheritInverseConfiguration
  List<UserDto> mapListDto(List<User> users);
}
