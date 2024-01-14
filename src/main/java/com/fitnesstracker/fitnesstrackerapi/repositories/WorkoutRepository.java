package com.fitnesstracker.fitnesstrackerapi.repositories;

import com.fitnesstracker.fitnesstrackerapi.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findAllByUserId(Long id);
}
