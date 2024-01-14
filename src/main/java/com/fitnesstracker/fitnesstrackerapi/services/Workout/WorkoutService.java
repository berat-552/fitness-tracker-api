package com.fitnesstracker.fitnesstrackerapi.services.Workout;

import com.fitnesstracker.fitnesstrackerapi.dtos.WorkoutDTO;
import com.fitnesstracker.fitnesstrackerapi.exceptions.InvalidFieldsException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.ResourceNotFoundException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstrackerapi.models.Workout;

import java.util.List;

public interface WorkoutService {
    WorkoutDTO createWorkout(Long userId, Workout workout) throws UserNotFoundException, InvalidFieldsException;

    WorkoutDTO getWorkout(Long id) throws ResourceNotFoundException;

    List<WorkoutDTO> getAllWorkouts(Long userId) throws UserNotFoundException, ResourceNotFoundException;

    Workout updateWorkout(Long userId, Workout workout) throws UserNotFoundException, ResourceNotFoundException;

    void deleteWorkout(Long id) throws UserNotFoundException, ResourceNotFoundException;
}
