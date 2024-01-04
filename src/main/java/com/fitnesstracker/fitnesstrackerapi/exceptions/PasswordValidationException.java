package com.fitnesstracker.fitnesstrackerapi.exceptions;

public class PasswordValidationException extends Exception {
    private static final String message = "Password must meet the following criteria:- At least one digit [0-9], At least one lowercase Latin character [a-z], At least one uppercase Latin character [A-Z], At least one special character like ! @ # & ( ) and Length of at least 8 characters and a maximum of 20 characters.";

    public PasswordValidationException() {
        super(message);
    }
}
