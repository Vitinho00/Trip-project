package com.viagem.trip_project.trip_records;

import java.time.LocalDateTime;

public record TripRequestUpdate(String destination, LocalDateTime starts_at, LocalDateTime ends_at) {
    
}
