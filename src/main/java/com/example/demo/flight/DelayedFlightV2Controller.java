package com.example.demo.flight;

import com.example.demo.flight.api.v2.FlightV2Response;
import com.example.demo.flight.api.v2.FlightsV2Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/api/v2/delayedflights")
@Tag(name = "Delayed Flights", description = "The delayed flights API")
public class DelayedFlightV2Controller {

    private final FlightService flightService;
    private final FlightV2Mapper mapper;

    @Autowired
    public DelayedFlightV2Controller(FlightService flightService, FlightV2Mapper mapper) {
        this.flightService = flightService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get All Delayed Flights", description = "Returns all delayed flights", tags = { "Delayed Flights" }
            ,extensions = {
            @Extension(properties = {
                    @ExtensionProperty(name = "x-42c-no-authentication", value = "true", parseValue = true)
            })})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(description =  "OK", implementation = FlightV2Response.class))),
    })
    @GetMapping
    public FlightsV2Response listDelayed() {
        List<Flight> allFlights = this.flightService.getAllDelayedFlights();

        return FlightsV2Response.builder()
                .flights(this.mapper.fromFlights(allFlights))
                .build();
    }
}
