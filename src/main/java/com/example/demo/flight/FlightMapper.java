package com.example.demo.flight;

import com.example.demo.v1.FlightApi;
import com.example.demo.v1.FlightRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightMapper {

    public FlightApi from(Flight flight) {
        return FlightApi.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(flight.getAirline())
                .arrivalTime(flight.getArrivalTime().toZonedDateTime())
                .departureTime(flight.getDepartureTime().toZonedDateTime())
                .price(flight.getPrice())
                .build();
    }

    public Flight toFlight(@Valid FlightRequest flightRequest) {
        return Flight.builder()
                .flightNumber(HtmlUtils.htmlEscape(flightRequest.getFlightNumber()))
                .airline(HtmlUtils.htmlEscape(flightRequest.getAirline()))
                .arrivalTime(flightRequest.getArrivalTime().toOffsetDateTime())
                .departureTime(flightRequest.getDepartureTime().toOffsetDateTime())
                .price(flightRequest.getPrice())
                .build();
    }

    public List<FlightApi> fromFlights(List<Flight> flights) {
        return flights.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }

}
