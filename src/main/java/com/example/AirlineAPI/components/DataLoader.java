package com.example.AirlineAPI.components;

import com.example.AirlineAPI.models.Flight;
import com.example.AirlineAPI.models.Passenger;
import com.example.AirlineAPI.repositories.FlightRepository;
import com.example.AirlineAPI.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    PassengerRepository passengerRepository;

    public DataLoader(){
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {

//        Virginia Airlines
        Flight virginiaAirlines = new Flight("Vice City", 100, LocalDate.of(1985,05,21), LocalTime.of(7,30));
        flightRepository.save(virginiaAirlines);

        Passenger tommyVercetti = new Passenger("Tommy Vercetti", "tvercetti@gmail.com");
        tommyVercetti.addFlight(virginiaAirlines);
        passengerRepository.save(tommyVercetti);

        Passenger ezioAuditore = new Passenger("Ezio Auditore", "eauditore@gmail.com");
        ezioAuditore.addFlight(virginiaAirlines);
        passengerRepository.save(ezioAuditore);

//      Fly Emirates
        Flight flyEmirates = new Flight("Italy", 300, LocalDate.of(1452, 05,22),LocalTime.of(8,35));
        flightRepository.save(flyEmirates);

        Passenger carlJohnson = new Passenger("Carl Johnson", "cjohnson@gmail.com");
        carlJohnson.addFlight(flyEmirates);
        passengerRepository.save(carlJohnson);

        Passenger milesMorales = new Passenger("Miles Morales", "mmorales@gmail.com");
        milesMorales.addFlight(flyEmirates);
        passengerRepository.save(milesMorales);


//        British Airways
        Flight britishAirways = new Flight("USA", 250, LocalDate.of(2023, 05, 23), LocalTime.of(9,30));
        flightRepository.save(britishAirways);

        Passenger ashKetchum = new Passenger("Ash Ketchum", "aketchum@gmail.com");
        ashKetchum.addFlight(britishAirways);
        passengerRepository.save(ashKetchum);

        Passenger kratosIcarus = new Passenger("Kratos Icarus", "kicarus@gmail.com");
        kratosIcarus.addFlight(britishAirways);
        passengerRepository.save(kratosIcarus);


    }



}