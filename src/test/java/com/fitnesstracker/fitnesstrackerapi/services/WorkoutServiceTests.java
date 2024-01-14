package com.fitnesstracker.fitnesstrackerapi.services;

import com.fitnesstracker.fitnesstrackerapi.dtos.WorkoutDTO;
import com.fitnesstracker.fitnesstrackerapi.exceptions.InvalidFieldsException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.ResourceNotFoundException;
import com.fitnesstracker.fitnesstrackerapi.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstrackerapi.models.Exercise;
import com.fitnesstracker.fitnesstrackerapi.models.User;
import com.fitnesstracker.fitnesstrackerapi.models.Workout;
import com.fitnesstracker.fitnesstrackerapi.repositories.ExerciseRepository;
import com.fitnesstracker.fitnesstrackerapi.repositories.UserRepository;
import com.fitnesstracker.fitnesstrackerapi.repositories.WorkoutRepository;
import com.fitnesstracker.fitnesstrackerapi.services.Workout.WorkoutServiceImpl;
import com.fitnesstracker.fitnesstrackerapi.util.WorkoutValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceTests {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private WorkoutValidator workoutValidator;

    @InjectMocks
    private WorkoutServiceImpl workoutService;

    @Test
    public void WorkoutService_CreateWorkout_ReturnsWorkoutNotNull() throws InvalidFieldsException, UserNotFoundException {
        Workout workout = Workout.TestWorkout();
        List<Exercise> exerciseList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Exercise exercise = Exercise.builder()
                    .name("Test exercise " + i)
                    .description("Exercise description" + i)
                    .workout(workout)
                    .build();

            exerciseList.add(exercise);
        }

        workout.setExercises(exerciseList);

        User user = User.TestUser();
        user.setWorkouts(List.of(workout));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
        doNothing().when(workoutValidator).isValidWorkoutFields(workout);

        WorkoutDTO result = workoutService.createWorkout(user.getId(), workout);

        assertNotNull(result);
        verify(userRepository).findById(user.getId());
        verify(workoutRepository).save(workout);
    }

    @Test
    public void WorkoutService_GetWorkout_ReturnsWorkoutNotNull() throws ResourceNotFoundException {
        Workout workout = Workout.TestWorkout();
        workout.setId(1L);

        List<Exercise> exerciseList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Exercise exercise = Exercise.builder()
                    .name("Test exercise " + i)
                    .description("Exercise description" + i)
                    .workout(workout)
                    .build();

            exerciseList.add(exercise);
        }

        workout.setExercises(exerciseList);

        when(workoutRepository.findById(anyLong())).thenReturn(Optional.of(workout));

        WorkoutDTO foundWorkout = workoutService.getWorkout(workout.getId());

        verify(workoutRepository, times(1)).findById(workout.getId());

        assertNotNull(foundWorkout);
        assertEquals(workout.getId(), foundWorkout.getId());
        verify(workoutRepository, times(1)).findById(workout.getId());
    }

    @Test
    public void WorkoutService_GetAllWorkouts_ReturnsAllWorkoutsForUser() throws UserNotFoundException, ResourceNotFoundException {
        List<Workout> allWorkouts = new ArrayList<>();
        User user = User.TestUser();
        user.setId(1L);

        for (int i = 0; i < 5; i++) {
            Workout testWorkout = Workout.TestWorkout();
            testWorkout.setExercises(List.of(Exercise.builder()
                    .name("Test exercise " + i)
                    .description("Exercise description" + i)
                    .workout(testWorkout)
                    .build()));

            allWorkouts.add(testWorkout);
        }

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(workoutRepository.findAllByUserId(user.getId())).thenReturn(allWorkouts);

        List<WorkoutDTO> foundWorkouts = workoutService.getAllWorkouts(user.getId());

        assertNotNull(foundWorkouts);
        assertThat(foundWorkouts.size()).isGreaterThan(4);
        verify(userRepository, times(1)).findById(user.getId());
        verify(workoutRepository, times(1)).findAllByUserId(user.getId());
    }
}
