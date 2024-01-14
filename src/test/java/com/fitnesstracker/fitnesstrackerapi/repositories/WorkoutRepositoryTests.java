package com.fitnesstracker.fitnesstrackerapi.repositories;

import com.fitnesstracker.fitnesstrackerapi.models.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class WorkoutRepositoryTests {

    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutRepositoryTests(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Test
    public void WorkoutRepository_Save_ReturnSavedWorkout() {
        Workout workout = Workout.TestWorkout();

        Workout savedWorkout = workoutRepository.save(workout);

        assertThat(savedWorkout).isNotNull();
        assertThat(savedWorkout.getId()).isGreaterThan(0);
    }

    @Test
    public void WorkoutRepository_FindById_ReturnWorkoutNotNull() {
        Workout workout = Workout.TestWorkout();
        workoutRepository.save(workout);

        Workout foundWorkout = workoutRepository.findById(workout.getId()).orElse(null);

        assertThat(foundWorkout).isNotNull();
        assertThat(foundWorkout).isSameAs(workout);
    }

    @Test
    public void WorkoutRepository_Update_ReturnWorkoutNotNull() {
        Workout workout = Workout.TestWorkout();

        workoutRepository.save(workout);

        String updatedWorkoutName = ("Workout B - Updated");

        workout.setName(updatedWorkoutName);

        Workout updatedWorkout = workoutRepository.save(workout);

        assertThat(updatedWorkout).isNotNull();
        assertThat(updatedWorkout.getDescription()).isNotNull();
        assertThat(updatedWorkout.getName()).isEqualTo(updatedWorkoutName);
    }

    @Test
    public void WorkoutRepository_DeleteById_ReturnWorkoutIsEmpty() {
        Workout workout = Workout.TestWorkout();
        workoutRepository.save(workout);

        workoutRepository.deleteById(workout.getId());
        Optional<Workout> workoutReturn = workoutRepository.findById(workout.getId());

        assertThat(workoutReturn).isEmpty();
    }
}
