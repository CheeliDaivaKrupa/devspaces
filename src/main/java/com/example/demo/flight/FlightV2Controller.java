package com.example.demo.flight;

import com.example.demo.flight.api.v2.FlightV2Request;
import com.example.demo.flight.api.v2.FlightV2Response;
import com.example.demo.flight.api.v2.FlightsV2Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "/api/v2/flights")
@Tag(name = "Flights V2", description = "The flights API")
public class FlightV2Controller {

    private final FlightService flightService;
    private final FlightV2Mapper mapper;

//    @Value("${info.app.config.server.encrypted.text}")
//    String testValue;

    @Autowired
    public FlightV2Controller(FlightService flightService, FlightV2Mapper mapper) {
        this.flightService = flightService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get Flight by ID", description = "Returns a flight for the given ID",
            tags = { "Flights V2" }, extensions = {
            @Extension(properties = {
                    @ExtensionProperty(name = "x-42c-no-authentication", value = "true", parseValue = true)
            })})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FlightV2Response.class))),
    })
    @GetMapping("/{id}")
    public FlightV2Response get(@Parameter(description="Flight ID",
            schema = @Schema(description = "Flight ID", type = "integer", format = "int32"))
            @PathVariable @Valid @Min(1) @Max(Integer.MAX_VALUE) Long id) throws Exception {

        Optional<Flight> flight = this.flightService.getFlight(id);
        if (flight.isEmpty()) {
            throw new Exception("Flight not found");
        }

        return FlightV2Response.builder()
                .flight(this.mapper.from(flight.get()))
                .build();
    }

    @Operation(summary = "Get All Flights", description = "Returns all flights", tags = { "Flights V2"}, extensions = {
            @Extension(properties = {
                    @ExtensionProperty(name = "x-42c-no-authentication", value = "true", parseValue = true)
            })})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FlightV2Response.class))),
    })
    @GetMapping
    public FlightsV2Response list() {
//        System.out.println("print test value.."+ testValue);
        List<Flight> allFlights = this.flightService.getAllFlights();

        return FlightsV2Response.builder()
                .flights(this.mapper.fromFlights(allFlights))
                .build();
    }

    @Operation(summary = "Create a Flight", description = "Creates a flight based on the given input",
            tags = { "Flights V2" }, extensions = {
            @Extension(properties = {
                    @ExtensionProperty(name = "x-42c-no-authentication", value = "true", parseValue = true)
            })})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FlightV2Response.class))),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightV2Response create(@Parameter(description="Flight Request to create a flight")
                                       @RequestBody @Valid FlightV2Request flightV2Request) {
        var newFlight = this.mapper.toFlight(flightV2Request);
        var flightCreated = this.flightService.create(newFlight);

        return FlightV2Response.builder()
                .flight(this.mapper.from(flightCreated))
                .build();
    }

    @Operation(summary = "Update a Flight", description = "Update a flight for the given ID based on the given input",
            tags = { "Flights V2" }, extensions = {
            @Extension(properties = {
                    @ExtensionProperty(name = "x-42c-no-authentication", value = "true", parseValue = true)
            })})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FlightV2Response.class))),
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public FlightV2Response update(
            @Parameter(description="Flight ID", schema = @Schema(description = "Flight ID", type = "integer", format = "int32"))
                                   @PathVariable @Valid @Min(1) @Max(Integer.MAX_VALUE) Long id,
                                   @Parameter(description="Flight Request to update a flight")
                                   @RequestBody @Valid FlightV2Request flightV2Request) throws Exception {

        Optional<Flight> currentFlight = this.flightService.getFlight(id);
        if (currentFlight.isEmpty()) {
            throw new Exception("Flight not found");
        }

        var flightUpdated = this.flightService.update(
                this.mapper.toUpdateFlight(flightV2Request, currentFlight.get()));

        return FlightV2Response.builder()
                .flight(this.mapper.from(flightUpdated))
                .build();
    }

    @Operation(summary = "Delete Flight by ID", description = "Deletes a flight for the given ID",
            tags = { "Flights V2" }, extensions = {
            @Extension(properties = {
                    @ExtensionProperty(name = "x-42c-no-authentication", value = "true", parseValue = true)
            })})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description="Flight ID", schema = @Schema(description = "Flight ID", type = "integer", format = "int32"))
            @PathVariable @Valid @Min(1) @Max(Integer.MAX_VALUE) Long id) throws Exception {

        Optional<Flight> flight = this.flightService.getFlight(id);
        if (flight.isEmpty()) {
            throw new Exception("Flight not found");
        }

        this.flightService.delete(id);
    }
}