package com.fitnesstracker.fitnesstrackerapi.repositories;

import com.fitnesstracker.fitnesstrackerapi.models.Exercise;
import com.fitnesstracker.fitnesstrackerapi.models.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
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
        assertThat(savedWorkout.getUser()).isNotNull();
        assertThat(savedWorkout.getExercises()).isNotNull();
    }

    @Test
    public void WorkoutRepository_FindById_ReturnWorkoutNotNull() {
        Workout workout = Workout.TestWorkout();
        workoutRepository.save(workout);

        Workout foundWorkout = workoutRepository.findById(workout.getId()).orElse(null);

        assertThat(foundWorkout).isNotNull();
        assertThat(foundWorkout.getUser()).isNotNull();
        assertThat(foundWorkout).isSameAs(workout);
        assertThat(foundWorkout.getExercises()).isNotNull();
    }

    @Test
    public void WorkoutRepository_Update_ReturnWorkoutNotNull() {
        Workout workout = Workout.TestWorkout();
        workoutRepository.save(workout);

        String updatedWorkoutName = "Workout B - Updated";
        List<Exercise> updatedExercises = Exercise.TestExercises(6);

        workout.setName(updatedWorkoutName);
        workout.setExercises(updatedExercises);

        Workout updatedWorkout = workoutRepository.save(workout);

        assertThat(updatedWorkout).isNotNull();
        assertThat(updatedWorkout.getDescription()).isNotNull();
        assertThat(updatedWorkout.getUser()).isNotNull();
        assertThat(updatedWorkout.getName()).isEqualTo(updatedWorkoutName);
        assertThat(updatedWorkout.getExercises()).isEqualTo(updatedExercises);
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
