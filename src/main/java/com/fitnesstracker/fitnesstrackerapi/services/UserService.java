package com.fitnesstracker.fitnesstrackerapi.services;

import com.fitnesstracker.fitnesstrackerapi.dtos.UserDTO;
import com.fitnesstracker.fitnesstrackerapi.exceptions.InvalidEmailException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.PasswordValidationException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.UserExistsException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstrackerapi.models.User;

public interface UserService {
    UserDTO createUser(User user) throws UserExistsException, InvalidEmailException, PasswordValidationException;

    UserDTO getUser(Long id) throws UserNotFoundException;

    UserDTO updateUser(Long id, User user) throws UserNotFoundException, UserExistsException, InvalidEmailException, PasswordValidationException;

    void deleteUser(Long id) throws UserNotFoundException;
}