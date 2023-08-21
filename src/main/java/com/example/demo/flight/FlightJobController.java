package com.example.demo.flight;

import com.example.demo.flight.api.v2.FlightV2Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequestMapping(path = "/api/v1/flightjobs")
@Tag(name = "Flights Batch Job", description = "The flights job API")
public class FlightJobController {

    private final FlightService flightService;
    private final FlightV2Mapper flightV2Mapper;

    @Autowired
    public FlightJobController(FlightService flightService, FlightV2Mapper flightV2Mapper) {
        this.flightService = flightService;
        this.flightV2Mapper = flightV2Mapper;
    }

    @Operation(summary = "Create Flight - Batch Job",
            description = "Create a flight based on the given input - used by Batch Job (OAuth2 protected)",
            tags = {"Flights Batch Job"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = FlightV2Api.class))),
    })
    @GetMapping
    public FlightV2Api createFlight() {
        var generatedFlightNumber = flightService.getGeneratedFlightNumber();
        long generatedRandomPrice = flightService.getRandomPriceForFlight();

        var newFlightCreatedByBatchJob = Flight.builder()
                .flightNumber(generatedFlightNumber)
                .airline("Batch Airlines " + generatedFlightNumber)
                .arrivalTime(OffsetDateTime.now())
                .departureTime(OffsetDateTime.now())
                .price(generatedRandomPrice)
                .delay(Boolean.FALSE).build();

        var flight = flightService.create(newFlightCreatedByBatchJob);
        return flightV2Mapper.from(flight);
    }
}
