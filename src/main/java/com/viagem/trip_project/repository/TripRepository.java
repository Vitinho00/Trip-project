package com.viagem.trip_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viagem.trip_project.model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long>{
}
