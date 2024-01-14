package com.fitnesstracker.fitnesstrackerapi.exceptions;


public class InvalidFieldsException extends Exception {
    public InvalidFieldsException(String[] requiredFields) {
        super("Please provide all fields: " + String.join(", ", requiredFields));
    }
}
