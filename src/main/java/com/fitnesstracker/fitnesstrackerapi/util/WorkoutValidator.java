package com.fitnesstracker.fitnesstrackerapi.util;

import com.fitnesstracker.fitnesstrackerapi.constants.Constants;
import com.fitnesstracker.fitnesstrackerapi.exceptions.InvalidFieldsException;
import com.fitnesstracker.fitnesstrackerapi.models.Workout;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WorkoutValidator {

    public void isValidWorkoutFields(Workout workoutObj) throws InvalidFieldsException {
        if (workoutObj.getName() == null || workoutObj.getDescription().isEmpty() ||
                workoutObj.getExercises() == null || workoutObj.getUser() == null) {
            throw new InvalidFieldsException(Constants.requiredWorkoutFields);
        }
    }
}
