package com.example.demo.flight;

import com.example.demo.v1.FlightRequest;
import com.example.demo.v1.FlightResponse;
import com.example.demo.v1.FlightsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "/api/v1/flights")
@Tag(name = "Flights V1", description = "The flights API")
public class FlightController {

    private final FlightService flightService;
    private final FlightMapper mapper;

    @Autowired
    public FlightController(FlightService flightService, FlightMapper mapper) {
        this.flightService = flightService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get Flight by ID", description = "Returns a flight for the given ID (OAuth2 protected)", tags = { "Flights V1"} )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FlightResponse.class))),
    })
    @GetMapping("/{id}")
    public FlightResponse get(
            @Parameter(description="Flight ID", schema = @Schema(description = "Flight ID", type = "integer", format = "int32"))
            @PathVariable @Valid @Min(1) @Max(Integer.MAX_VALUE) Long id) throws Exception {

        Optional<Flight> flight = this.flightService.getFlight(id);
        if (flight.isEmpty()) {
            throw new Exception("Flight not found");
        }

        return FlightResponse.builder()
                .flight(this.mapper.from(flight.get()))
                .build();
    }

    @Operation(summary = "Get All Flights", description = "Returns all flights (OAuth2 protected)", tags = { "Flights V1"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FlightResponse.class))),
    })
    @GetMapping
    public FlightsResponse list() {
        List<Flight> allFlights = this.flightService.getAllFlights();

        return FlightsResponse.builder()
                .flights(this.mapper.fromFlights(allFlights))
                .build();
    }

    @Operation(summary = "Create a Flight", description = "Creates a flight based on the given input (OAuth2 protected)", tags = { "Flights V1"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FlightResponse.class))),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse create(@Parameter(description="Flight Request to create a flight")
                                     @RequestBody @Valid FlightRequest flightRequest) {
        var newFlight = this.mapper.toFlight(flightRequest);
        var flightCreated = this.flightService.create(newFlight);

        return FlightResponse.builder()
                .flight(this.mapper.from(flightCreated))
                .build();
    }
}
