package com.example.AirlineAPI.repositories;

import com.example.AirlineAPI.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
