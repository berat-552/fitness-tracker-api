package com.fitnesstracker.fitnesstrackerapi.services;

import com.fitnesstracker.fitnesstrackerapi.dtos.UserDTO;
import com.fitnesstracker.fitnesstrackerapi.exceptions.*;
import com.fitnesstracker.fitnesstrackerapi.models.User;

public interface UserService {
    UserDTO createUser(User user) throws UserExistsException, InvalidEmailException, PasswordValidationException, InvalidFieldsException, NoSuchFieldException, IllegalAccessException;

    UserDTO getUser(Long id) throws UserNotFoundException;

    UserDTO updateUser(Long id, User user) throws UserNotFoundException, UserExistsException, InvalidEmailException, PasswordValidationException, InvalidFieldsException;

    void deleteUser(Long id) throws UserNotFoundException;
}