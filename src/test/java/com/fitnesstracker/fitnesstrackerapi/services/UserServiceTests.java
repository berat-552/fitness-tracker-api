package com.fitnesstracker.fitnesstrackerapi.services;

import com.fitnesstracker.fitnesstrackerapi.dtos.UserDTO;
import com.fitnesstracker.fitnesstrackerapi.exceptions.InvalidEmailException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.PasswordValidationException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.UserExistsException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstrackerapi.models.User;
import com.fitnesstracker.fitnesstrackerapi.repositories.UserRepository;
import com.fitnesstracker.fitnesstrackerapi.util.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void UserService_CreateUser_ReturnsUser() throws UserExistsException, InvalidEmailException, PasswordValidationException {
        User user = User.TestUser();

        doNothing().when(userValidator).isValidUser(anyString(), anyString());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO savedUser = userService.createUser(user);

        assertThat(savedUser).isNotNull();
    }

    @Test
    public void UserService_GetUser_ReturnsUser() throws UserNotFoundException {
        User user = User.TestUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO retrievedUser = userService.getUser(user.getId());

        assertThat(retrievedUser).isNotNull();
        verify(userRepository).findById(user.getId());
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    public void UserService_UpdateUser_ReturnsUpdatedUser() throws UserNotFoundException, UserExistsException, InvalidEmailException {
        User initialUser = User.TestUser();

        when(userRepository.findById(initialUser.getId())).thenReturn(Optional.of(initialUser));
        when(userValidator.isValidEmail(anyString())).thenReturn(true);
        when(userValidator.userExists(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(initialUser);

        String updatedUsername = "updated_username54";
        double updatedWeight = 67.0;

        initialUser.setUsername(updatedUsername);
        initialUser.setWeightKg(updatedWeight);

        UserDTO updatedUser = userService.updateUser(initialUser.getId(), initialUser);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo(updatedUsername);
        assertThat(updatedUser.getWeightKg()).isEqualTo(updatedWeight);

        // Verify that methods were called
        verify(userRepository).findById(initialUser.getId());
        verify(userValidator).isValidEmail(initialUser.getEmail());
        verify(userValidator).userExists(initialUser.getEmail());
        verify(userRepository).save(any(User.class));
    }


    @Test
    public void UserService_DeleteUser_ReturnsVoid() throws UserNotFoundException {
        User user = User.TestUser();

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userRepository.save(user);
        userService.deleteUser(user.getId());

        assertThatCode(() -> userService.deleteUser(user.getId()))
                .doesNotThrowAnyException();
        assertAll(() -> userService.deleteUser(user.getId()));
    }
}
