package com.viagem.trip_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viagem.trip_project.model.Activities;


public interface ActivitiesRepository extends JpaRepository<Activities, Long> {
   List<Activities> findByTripId(Long id);
}
