package ru.simbirsoft.warehouse_management.service;

import ru.simbirsoft.warehouse_management.dto.UserDto;
import ru.simbirsoft.warehouse_management.dto.createForms.SignUpDto;

public interface SignUpService {

    /**
     * Sign up to the application. If sign up is successfully, then creates a user in db.
     * Checks uniqueness of username and email. If not unique then throw exception
     *
     * @param signUpData - user info create form with username, password, email, role
     * @return UserDto - information about new user with id
     */
    UserDto signUp(SignUpDto signUpData);
}
