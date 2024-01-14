package com.fitnesstracker.fitnesstrackerapi.controllers;

import com.fitnesstracker.fitnesstrackerapi.dtos.UserDTO;
import com.fitnesstracker.fitnesstrackerapi.exceptions.*;
import com.fitnesstracker.fitnesstrackerapi.models.User;
import com.fitnesstracker.fitnesstrackerapi.services.User.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) throws UserExistsException, InvalidEmailException, PasswordValidationException, InvalidFieldsException {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User user) throws UserNotFoundException, InvalidEmailException, PasswordValidationException, InvalidFieldsException {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
