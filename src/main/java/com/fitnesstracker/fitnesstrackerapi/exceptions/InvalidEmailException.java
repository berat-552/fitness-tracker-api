package com.fitnesstracker.fitnesstrackerapi.exceptions;

public class InvalidEmailException extends Exception {

    public InvalidEmailException() {
        super("Invalid email format");
    }
}
