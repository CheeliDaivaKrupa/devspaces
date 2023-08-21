package com.example.demo.v1;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Flight API V1")
public class FlightApi {

    @NotNull
    @Min(1)
    @Max(Integer.MAX_VALUE)
    @Schema(description = "Flight ID")
    Long id;

    @NotNull
    @Size(min = 1, max = 9, message = "flightNumber must be between 1 and 9 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Schema(example = "F101", description = "Flight Number of the flight")
    String flightNumber;

    @NotNull
    @Size(min = 1, max = 200, message = "airline must be between 1 and 200 characters")
    @Pattern(regexp = "^[-_ a-zA-Z0-9]+$")
    @Schema(description = "Airline Name")
    String airline;

    @Schema(description = "Arrival Time of the flight", minLength = 10, maxLength = 27)
    ZonedDateTime arrivalTime;

    @NotNull
    @Schema(description = "Departure Time of the flight", minLength = 10, maxLength = 27)
    ZonedDateTime departureTime;

    @NotNull
    @Min(1)
    @Max(Integer.MAX_VALUE)
    @Schema(description = "Flight Price")
    Long price;
}
