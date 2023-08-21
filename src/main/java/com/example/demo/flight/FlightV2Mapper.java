package com.example.demo.flight;

import com.example.demo.flight.api.v2.FlightV2Api;
import com.example.demo.flight.api.v2.FlightV2Request;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightV2Mapper {

    public FlightV2Api from(Flight flight) {
        return FlightV2Api.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(flight.getAirline())
                .arrivalTime(flight.getArrivalTime().toZonedDateTime())
                .departureTime(flight.getDepartureTime().toZonedDateTime())
                .price(flight.getPrice())
                .delay(flight.isDelay())
                .build();
    }

    public Flight toFlight(FlightV2Request flightRequest) {
        return Flight.builder()
                .flightNumber(HtmlUtils.htmlEscape(flightRequest.getFlightNumber()))
                .airline(HtmlUtils.htmlEscape(flightRequest.getAirline()))
                .arrivalTime(flightRequest.getArrivalTime().toOffsetDateTime())
                .departureTime(flightRequest.getDepartureTime().toOffsetDateTime())
                .price(flightRequest.getPrice())
                .delay(flightRequest.getDelay())
                .build();
    }

    public Flight toUpdateFlight(FlightV2Request flightRequest, Flight currentFlight) {
        currentFlight.setFlightNumber(HtmlUtils.htmlEscape(flightRequest.getFlightNumber()));
        currentFlight.setAirline(HtmlUtils.htmlEscape(flightRequest.getAirline()));
        currentFlight.setArrivalTime(flightRequest.getArrivalTime().toOffsetDateTime());
        currentFlight.setDepartureTime(flightRequest.getDepartureTime().toOffsetDateTime());
        currentFlight.setPrice(flightRequest.getPrice());
        currentFlight.setDelay(flightRequest.getDelay());
        return currentFlight;
    }

    public List<FlightV2Api> fromFlights(List<Flight> flights) {
        return flights.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }
}
