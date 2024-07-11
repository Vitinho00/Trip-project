package com.viagem.trip_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viagem.trip_project.model.Participant;
import com.viagem.trip_project.model.Trip;
import com.viagem.trip_project.repository.ParticipantRepository;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;
    
    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip) {
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(trip, email)).toList();
        this.participantRepository.saveAll(participants);
    }

    public void triggerConfirmationEmailToParticipant(Long tripId) {};

    public List<Participant> getAllParticipants(Long tripId) {
        return participantRepository.findByTripId(tripId);
    };
}
