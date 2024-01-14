package com.fitnesstracker.fitnesstrackerapi.repositories;

import com.fitnesstracker.fitnesstrackerapi.dtos.ExerciseDTO;
import com.fitnesstracker.fitnesstrackerapi.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<ExerciseDTO> findAllByWorkoutId(Long workoutId);
}
