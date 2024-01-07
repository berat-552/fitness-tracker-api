package com.fitnesstracker.fitnesstrackerapi.models;

import com.fitnesstracker.fitnesstrackerapi.dtos.WorkoutDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workout_id")
    private List<Exercise> exercises;

    public static List<Workout> TestWorkouts(int numWanted) {

        List<Workout> testWorkouts = new ArrayList<>();

        for (int i = 0; i < numWanted; i++) {
            testWorkouts.add(
                    Workout.builder()
                            .name("Workout name: " + i)
                            .description("Workout description: " + i)
                            .exercises(Exercise.TestExercises(numWanted))
                            .build()
            );
        }

        return testWorkouts;
    }

    public static Workout TestWorkout() {
        return Workout.builder()
                .name("Ab workout")
                .description("High intensity core workout")
                .user(User.builder().build())
                .exercises(Exercise.TestExercises(5))
                .build();
    }

    public WorkoutDTO workoutDTO() {
        return new WorkoutDTO(id, name, description, user.getId(), exercises.stream()
                .map(Exercise::exerciseDTO)
                .collect(Collectors.toList()));
    }
}
