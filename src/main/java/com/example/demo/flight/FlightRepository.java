package com.example.demo.flight;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends Repository<Flight, Long> {

    List<Flight> findAll();

    Flight save(Flight flight);

    Optional<Flight> findById(Long id);

    @Query(value = "select * from cab_ford_air_flight where delay=true", nativeQuery = true)
    List<Flight> findAllDelayedFlights();

    boolean existsById(Long id);

    void deleteById(Long id);
}
