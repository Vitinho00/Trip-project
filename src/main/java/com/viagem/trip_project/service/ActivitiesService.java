package com.viagem.trip_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viagem.trip_project.activity_records.ActivitiesRequestPayload;
import com.viagem.trip_project.model.Activities;
import com.viagem.trip_project.model.Trip;
import com.viagem.trip_project.repository.ActivitiesRepository;

@Service
public class ActivitiesService {

        @Autowired
        private ActivitiesRepository activitiesRepository;
    
        public void registerActivitiesToEvent(ActivitiesRequestPayload payload, Trip trip) {
        Activities activities = new Activities(payload, trip);
        activitiesRepository.save(activities);
    }

    public List<Activities> findActivitiesByTrip(Long id) {
        return activitiesRepository.findByTripId(id);
    }
}
