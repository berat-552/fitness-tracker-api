package com.fitnesstracker.fitnesstrackerapi.services.User;

import com.fitnesstracker.fitnesstrackerapi.dtos.UserDTO;
import com.fitnesstracker.fitnesstrackerapi.exceptions.*;
import com.fitnesstracker.fitnesstrackerapi.models.User;
import com.fitnesstracker.fitnesstrackerapi.repositories.UserRepository;
import com.fitnesstracker.fitnesstrackerapi.util.BCryptUtils;
import com.fitnesstracker.fitnesstrackerapi.util.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Override
    public UserDTO createUser(User user) throws UserExistsException, InvalidEmailException, PasswordValidationException, InvalidFieldsException {
        userValidator.isValidUser(user.getEmail(), user.getPassword());

        userValidator.validateUserFields(user);

        String hashedPassword = BCryptUtils.hashPassword(user.getPassword());

        user.setPassword(hashedPassword);
        userRepository.save(user);

        return user.userDTO();
    }

    @Override
    public UserDTO getUser(Long id) throws UserNotFoundException {
        User foundUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        return foundUser.userDTO();
    }

    @Override
    public UserDTO updateUser(Long id, User user) throws UserNotFoundException, InvalidEmailException, PasswordValidationException, InvalidFieldsException {
        User existingUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!userValidator.isValidEmail(user.getEmail()))
            throw new InvalidEmailException();

        userValidator.validateUserFields(user);

        String hashedPassword = BCryptUtils.hashPassword(user.getPassword());

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(hashedPassword);
        existingUser.setEmail(user.getEmail());
        existingUser.setWeightKg(user.getWeightKg());
        existingUser.setHeightCm(user.getHeightCm());
        existingUser.setDob(user.getDob());

        userRepository.save(existingUser);

        return existingUser.userDTO();
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
