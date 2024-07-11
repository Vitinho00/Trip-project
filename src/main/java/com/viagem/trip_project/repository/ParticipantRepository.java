package com.viagem.trip_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viagem.trip_project.model.Participant;

import java.util.List;


@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByTripId(Long id);
}
