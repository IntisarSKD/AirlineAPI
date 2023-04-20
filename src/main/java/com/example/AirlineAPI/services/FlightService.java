package com.example.AirlineAPI.services;

import com.example.AirlineAPI.models.Flight;
import com.example.AirlineAPI.repositories.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight createFlight(Flight flight) {
            return flightRepository.save(flight);
        }


    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight with id " + id + " not found"));
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}
