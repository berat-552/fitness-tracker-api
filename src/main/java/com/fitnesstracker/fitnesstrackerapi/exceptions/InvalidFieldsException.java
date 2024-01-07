package com.fitnesstracker.fitnesstrackerapi.exceptions;


public class InvalidFieldsException extends Exception {
    public InvalidFieldsException() {
        super("Please provide all fields (username, email, password, dob, weightKg, heightCm");
    }
}
