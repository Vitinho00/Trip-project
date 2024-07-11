package com.viagem.trip_project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.viagem.trip_project.model.Participant;
import com.viagem.trip_project.participant_records.ParticipantRequestPayload;
import com.viagem.trip_project.repository.ParticipantRepository;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/participant")
public class ParticipantController {

    @Autowired
    private ParticipantRepository participantRepository;
    
    @PostMapping("/{id}/confirm")
    public ResponseEntity<String> confirmParticipant(@PathVariable Long id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Participant> partOptional = this.participantRepository.findById(id);
        if(!partOptional.isPresent()) {
            throw new RuntimeException("Participante não encontrado!");
        }
        Participant participant = partOptional.get();
        participant.setName(payload.name());
        participant.setConfirmed(true);
        participantRepository.save(participant);
        
        return ResponseEntity.ok(participant.getName() + " aceitou o convite!");
    }
    
}
