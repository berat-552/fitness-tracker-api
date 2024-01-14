package com.fitnesstracker.fitnesstrackerapi.models;

import com.fitnesstracker.fitnesstrackerapi.dtos.WorkoutDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static Workout TestWorkout() {
        return Workout.builder()
                .name("Ab workout")
                .description("High intensity core workout")
                .build();
    }

    public WorkoutDTO workoutDTO() {
        return new WorkoutDTO(id, name, description, exercises.stream()
                .map(Exercise::exerciseDTO)
                .collect(Collectors.toList()));
    }
}
