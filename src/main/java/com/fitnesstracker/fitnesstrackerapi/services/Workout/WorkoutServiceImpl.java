package com.fitnesstracker.fitnesstrackerapi.services.Workout;

import com.fitnesstracker.fitnesstrackerapi.dtos.WorkoutDTO;
import com.fitnesstracker.fitnesstrackerapi.exceptions.InvalidFieldsException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.ResourceNotFoundException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstrackerapi.models.User;
import com.fitnesstracker.fitnesstrackerapi.models.Workout;
import com.fitnesstracker.fitnesstrackerapi.repositories.ExerciseRepository;
import com.fitnesstracker.fitnesstrackerapi.repositories.UserRepository;
import com.fitnesstracker.fitnesstrackerapi.repositories.WorkoutRepository;
import com.fitnesstracker.fitnesstrackerapi.util.WorkoutValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutValidator workoutValidator;

    @Override
    public WorkoutDTO createWorkout(Long userId, Workout workout) throws UserNotFoundException, InvalidFieldsException {
        User foundUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        workoutValidator.isValidWorkoutFields(workout);

        workout.setUser(foundUser);
        return workoutRepository.save(workout).workoutDTO();
    }

    @Override
    public WorkoutDTO getWorkout(Long id) throws ResourceNotFoundException {
        Workout foundWorkout = workoutRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        return foundWorkout.workoutDTO();
    }

    @Override
    public List<WorkoutDTO> getAllWorkouts(Long userId) throws UserNotFoundException, ResourceNotFoundException {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Workout> allWorkouts = workoutRepository.findAllByUserId(userId);

        if (allWorkouts.isEmpty())
            throw new ResourceNotFoundException(userId);

        return allWorkouts.stream()
                .map(workout -> {
                    WorkoutDTO workoutDTO = workout.workoutDTO();
                    workoutDTO.setExercises(exerciseRepository.findAllByWorkoutId(workout.getId()));
                    return workoutDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Workout updateWorkout(Long userId, Workout workout) throws UserNotFoundException, ResourceNotFoundException {
        return null;
    }

    @Override
    public void deleteWorkout(Long id) throws UserNotFoundException, ResourceNotFoundException {

    }
}
