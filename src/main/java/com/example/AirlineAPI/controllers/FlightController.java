package com.example.AirlineAPI.controllers;

import com.example.AirlineAPI.models.Flight;
import com.example.AirlineAPI.models.Passenger;
import com.example.AirlineAPI.repositories.FlightRepository;
import com.example.AirlineAPI.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping("")
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        return flight.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        flightRepository.save(flight);
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight newFlight) {
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();
            flight.setDestination(newFlight.getDestination());
            flight.setCapacity(newFlight.getCapacity());
            flight.setDepartureDate(newFlight.getDepartureDate());
            flight.setDepartureTime(newFlight.getDepartureTime());
            flightRepository.save(flight);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFlight(@PathVariable Long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        if (flight.isPresent()) {
            List<Passenger> passengers = flight.get().getPassengers();
            for (Passenger passenger : passengers) {
                passenger.removeFlight(flight.get());
                passengerRepository.save(passenger);
            }
            flightRepository.delete(flight.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{flightId}/passengers/{passengerId}")
    public ResponseEntity<HttpStatus> addPassengerToFlight(@PathVariable Long flightId, @PathVariable Long passengerId) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
        Optional<Passenger> optionalPassenger = passengerRepository.findById(passengerId);
        if (optionalFlight.isPresent() && optionalPassenger.isPresent()) {
            Flight flight = optionalFlight.get();
            Passenger passenger = optionalPassenger.get();
            if (!flight.getPassengers().contains(passenger)) {
                flight.addPassenger(passenger);
                passenger.addFlight(flight);
                flightRepository.save(flight);
                passengerRepository.save(passenger);
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{flightId}/passengers/{passengerId}")
    public ResponseEntity<HttpStatus> removePassengerFromFlight(@PathVariable Long flightId, @PathVariable Long passengerId) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
        Optional<Passenger> optionalPassenger = passengerRepository.findById(passengerId);

        if (optionalFlight.isPresent() && optionalPassenger.isPresent()) {
            Flight flight = optionalFlight.get();
            Passenger passenger = optionalPassenger.get();

            List<Passenger> passengers = flight.getPassengers();
            passengers.remove(passenger);

            flightRepository.save(flight);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

