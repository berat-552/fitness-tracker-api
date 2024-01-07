package com.fitnesstracker.fitnesstrackerapi.models;

import com.fitnesstracker.fitnesstrackerapi.dtos.ExerciseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public static List<Exercise> TestExercises(int numWanted) {

        List<Exercise> testExercises = new ArrayList<>();

        for (int i = 0; i < numWanted; i++) {
            testExercises.add(
                    Exercise.builder()
                            .name("Exercise name: " + i)
                            .description("Exercise Description: " + i)
                            .build()
            );
        }
        return testExercises;
    }

    public ExerciseDTO exerciseDTO() {
        return new ExerciseDTO(id, name, description, workout.getId());
    }
}
