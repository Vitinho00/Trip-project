package com.viagem.trip_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viagem.trip_project.link_records.LinkRequestPayload;
import com.viagem.trip_project.model.Link;
import com.viagem.trip_project.model.Trip;
import com.viagem.trip_project.repository.LinkRepository;

@Service
public class LinkService {
    
    @Autowired
    private LinkRepository linkRepository;

    public void registerLinkToTrip(LinkRequestPayload payload, Trip trip) {
        Link link = new Link(payload, trip);
        linkRepository.save(link);
    }

    public List<Link> findLinksByTrip(Long id) {
        return linkRepository.findByTripId(id);
    }
}
