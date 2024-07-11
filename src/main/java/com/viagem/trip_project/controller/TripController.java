package com.viagem.trip_project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.viagem.trip_project.activity_records.ActivitiesRequestPayload;
import com.viagem.trip_project.link_records.LinkRequestPayload;
import com.viagem.trip_project.model.Activities;
import com.viagem.trip_project.model.Link;
import com.viagem.trip_project.model.Participant;
import com.viagem.trip_project.model.Trip;
import com.viagem.trip_project.repository.TripRepository;
import com.viagem.trip_project.service.ActivitiesService;
import com.viagem.trip_project.service.LinkService;
import com.viagem.trip_project.service.ParticipantService;
import com.viagem.trip_project.trip_records.TripRequestId;
import com.viagem.trip_project.trip_records.TripRequestInvite;
import com.viagem.trip_project.trip_records.TripRequestPayload;
import com.viagem.trip_project.trip_records.TripRequestUpdate;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/trips")
public class TripController {
    
    @Autowired
    ParticipantService participantService;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    private ActivitiesService activitiesService;

    @Autowired
    private LinkService linkService;

    @PostMapping()
    public ResponseEntity<TripRequestId> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);

        this.tripRepository.save(newTrip);

        this.participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripRequestId(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getInfoTrip(@PathVariable Long id) {
      Optional<Trip> opTrip = tripRepository.findById(id);
      if(!opTrip.isPresent()) {
        throw new RuntimeException("Viagem nao encontrada!");
      }
      return ResponseEntity.ok(opTrip.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable Long id, @RequestBody TripRequestUpdate tripUpdated) {
        Optional<Trip> opTrip = tripRepository.findById(id);
        if(!opTrip.isPresent()) {
          throw new RuntimeException("Viagem nao encontrada!");
        }
        Trip newTrip = opTrip.get();
        newTrip.setDestination(tripUpdated.destination());
        newTrip.setEndsAt(tripUpdated.ends_at());
        newTrip.setStartsAt(tripUpdated.starts_at());

        tripRepository.save(newTrip);
        return ResponseEntity.status(HttpStatus.OK).body("Atualizado!");
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<?> confirmTrip(@PathVariable Long id) {
        Optional<Trip> opTrip = tripRepository.findById(id);
        if(!opTrip.isPresent()) {
          throw new RuntimeException("Viagem nao encontrada!");
        }
        Trip newTrip = opTrip.get();
        newTrip.setConfirmed(true);
        this.tripRepository.save(newTrip);

        this.participantService.triggerConfirmationEmailToParticipant(id);

        return ResponseEntity.status(HttpStatus.OK).body("Viagem confirmada!");
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<String> inviteParticipant(@PathVariable Long id, @RequestBody TripRequestInvite invite) {
        Optional<Trip> opTrip = tripRepository.findById(id);
        if(!opTrip.isPresent()) {
          throw new RuntimeException("Viagem nao encontrada!");
        }
        Trip newTrip = opTrip.get();
        if(!newTrip.isConfirmed()) throw new RuntimeException("A viagem ainda não está confirmada!");
        this.participantService.registerParticipantsToEvent(invite.emails_to_invite(), newTrip);

        
        this.tripRepository.save(newTrip);
        
        return ResponseEntity.ok("Convites enviados!");
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<Participant>> findParticipantByTrip(@PathVariable Long id) {
       List<Participant> allParticipants = this.participantService.getAllParticipants(id);
       return ResponseEntity.ok(allParticipants);
    }

    @PostMapping("/{id}/activity")
    public ResponseEntity<String> createActivity(@PathVariable Long id, @RequestBody ActivitiesRequestPayload payload) {
        Optional<Trip> opTrip = tripRepository.findById(id);
        if(!opTrip.isPresent()) {
          throw new RuntimeException("Viagem nao encontrada!");
        }
        this.activitiesService.registerActivitiesToEvent(payload, opTrip.get());
        
        return ResponseEntity.ok("Atividades registradas!");
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<Activities>> findActivityByTrip(@PathVariable Long id) {
       List<Activities> allActivities = activitiesService.findActivitiesByTrip(id);
       return ResponseEntity.ok(allActivities);
    }

    @PostMapping("/{id}/link")
    public ResponseEntity<String> createLink(@PathVariable Long id, @RequestBody LinkRequestPayload payload) {
        Optional<Trip> opTrip = tripRepository.findById(id);
        if(!opTrip.isPresent()) {
          throw new RuntimeException("Viagem nao encontrada!");
        }
        this.linkService.registerLinkToTrip(payload, opTrip.get());
        
        return ResponseEntity.ok("Link registrado!");
    }
    
    @GetMapping("/{id}/links")
    public ResponseEntity<List<Link>> findLinkByTrip(@PathVariable Long id) {
       List<Link> allLinks = linkService.findLinksByTrip(id);
       return ResponseEntity.ok(allLinks);
    }
    
    
}
