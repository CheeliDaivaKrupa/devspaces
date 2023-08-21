package com.example.demo.flight;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FlightService {

    private final FlightRepository flightsRepository;

    @Autowired
    public FlightService(FlightRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public Flight create(Flight newFlight) {
        var persistedFlight = flightsRepository.save(newFlight);
        log.warn("Created new flight --> " + HtmlUtils.htmlEscape(persistedFlight.flightNumber));
        return persistedFlight;
    }

    public Flight update(Flight updateFlight) {
        var persistedFlight = flightsRepository.save(updateFlight);
        log.warn("Updated flight --> " + HtmlUtils.htmlEscape(persistedFlight.flightNumber));
        //Note: the last modified user & date fields are automatically updated by listeners.
        return persistedFlight;
    }

    public void delete(Long id) {
        flightsRepository.deleteById(id);
    }

    public boolean isExists(Long id) {
        return flightsRepository.existsById(id);
    }

    public boolean notExists(Long id) {
        return !isExists(id);
    }

    public List<Flight> getAllFlights() {
        return flightsRepository.findAll();
    }

    public Optional<Flight> getFlight(Long id) {
        return flightsRepository.findById(id);
    }

    public List<Flight> getAllDelayedFlights() {
        return flightsRepository.findAllDelayedFlights();
    }

    protected String getGeneratedFlightNumber() {
        return getGeneratedFlightNumber(5);
    }

    private String getGeneratedFlightNumber(int retries) {
        long random = generateRandomNumber(4);
        var flightNumber = String.valueOf(random);

        var allFlights = this.getAllFlights();

        Optional<Flight> foundFlight = allFlights
                .stream()
                .filter(flight -> flightNumber.equals(flight.getFlightNumber()))
                .findFirst();

        //Check whether optional has element you are looking for
        if (foundFlight.isPresent()) {
            if (retries < 1) {
                return UUID.randomUUID().toString();
            } else {
                return this.getGeneratedFlightNumber(--retries);
            }
        } else {
            return flightNumber;
        }
    }

    protected long getRandomPriceForFlight() {
        return generateRandomNumber(3);
    }

    private long generateRandomNumber(int size) {
        var secRan = new SecureRandom();
        var ranBytes = new byte[20];
        secRan.nextBytes(ranBytes);

        var randomInt = ByteBuffer.wrap(ranBytes).getInt();
        randomInt = Math.abs(randomInt);

        var randomString = String.valueOf(randomInt);
        if (randomString.length() > size) {
            randomString = randomString.substring(randomString.length() - size);
        }

        return Integer.parseInt(randomString);
    }

}
