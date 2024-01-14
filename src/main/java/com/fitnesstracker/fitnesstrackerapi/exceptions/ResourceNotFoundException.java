package com.fitnesstracker.fitnesstrackerapi.exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(Long id) {
        super(String.format("Resource with id '%d' not found", id));
    }
}
