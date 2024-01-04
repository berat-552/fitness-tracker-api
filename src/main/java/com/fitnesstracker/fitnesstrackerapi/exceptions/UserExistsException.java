package com.fitnesstracker.fitnesstrackerapi.exceptions;

public class UserExistsException extends Exception {

    public UserExistsException(String email) {
        super("The user with email %s already exists".formatted(email));
    }
}
