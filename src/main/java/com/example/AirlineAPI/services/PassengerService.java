package com.example.AirlineAPI.services;

import com.example.AirlineAPI.models.Passenger;
import com.example.AirlineAPI.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;

    public Passenger createPassenger(Passenger passenger) {
        if (passenger.getEmailAddress() == null || passenger.getEmailAddress().isEmpty()) {
            throw new IllegalArgumentException("Passenger email address cannot be null or empty");
        }
        return passengerRepository.save(passenger);
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassengerById(Long id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        if (optionalPassenger.isPresent()) {
            return optionalPassenger.get();
        } else {
            throw new IllegalArgumentException("Invalid passenger ID");
        }
    }

    public void deletePassengerById(Long id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        if (optionalPassenger.isPresent()) {
            Passenger passenger = optionalPassenger.get();
            passenger.getFlights().forEach(flight -> flight.getPassengers().remove(passenger));
            passengerRepository.delete(passenger);
        } else {
            throw new IllegalArgumentException("Invalid passenger ID");
        }
    }
}
