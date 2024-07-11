package com.viagem.trip_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viagem.trip_project.model.Link;


public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByTripId(Long id);
}
