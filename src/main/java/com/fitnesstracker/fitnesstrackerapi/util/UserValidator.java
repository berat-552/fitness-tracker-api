package com.fitnesstracker.fitnesstrackerapi.util;

import com.fitnesstracker.fitnesstrackerapi.exceptions.InvalidEmailException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.PasswordValidationException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.UserExistsException;
import com.fitnesstracker.fitnesstrackerapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class UserValidator {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$");

    private final UserRepository userRepository;

    public boolean isValidEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {
        return PASSWORD_REGEX.matcher(password).matches();
    }

    public void isValidUser(String email, String password) throws InvalidEmailException, UserExistsException, PasswordValidationException {
        if (!isValidEmail(email)) {
            throw new InvalidEmailException();
        }

        if (userExists(email)) {
            throw new UserExistsException(email);
        }

        if (!isValidPassword(password)) {
            throw new PasswordValidationException();
        }
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
