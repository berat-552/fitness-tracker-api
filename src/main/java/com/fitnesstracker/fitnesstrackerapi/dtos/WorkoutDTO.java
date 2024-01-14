package com.fitnesstracker.fitnesstrackerapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDTO {
    private Long id;
    private String name;
    private String description;
    private List<ExerciseDTO> exercises;
}
