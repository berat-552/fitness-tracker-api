package com.fitnesstracker.fitnesstrackerapi.models;

import com.fitnesstracker.fitnesstrackerapi.dtos.ExerciseDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "exercises")
@ToString(exclude = "workout")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public ExerciseDTO exerciseDTO() {
        return new ExerciseDTO(id, name, description, workout.getId());
    }
}
