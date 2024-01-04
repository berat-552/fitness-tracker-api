package com.fitnesstracker.fitnesstrackerapi.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User does not exist");
    }
}
