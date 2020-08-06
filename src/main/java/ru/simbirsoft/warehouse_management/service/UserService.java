package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.UserDto;

public interface UserService{
    /**
     * Returns user with such id
     * @param id - id of the user
     * @return dto of finded user
     * @throws ru.simbirsoft.warehouse_management.exception.NotFoundException if entity cannot be found
     */
    UserDto getById(Long id);

}
