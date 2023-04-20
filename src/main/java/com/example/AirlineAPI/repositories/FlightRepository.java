package com.example.AirlineAPI.repositories;

import com.example.AirlineAPI.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
