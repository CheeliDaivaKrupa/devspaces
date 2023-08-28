package com.example.demo.flight.api.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Flight V2 Request to create/update a flight")
public class FlightV2Request {

    @NotNull
    @Size(min = 1, max = 9, message = "flightNumber must be between 1 and 9 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Schema(example = "F101", description = "V2 Flight Number of the flight")
    String flightNumber;

    @NotNull
    @Size(min = 1, max = 50, message = "airline must be between 1 and 200 characters")
    @Pattern(regexp = "^[-_ a-zA-Z0-9]+$")
    @Schema(description = "V2 Airline Name")
    String airline;

    @NotNull
    @Schema(example = "2021-10-20T17:11:44.074334Z", description = "V2 Arrival Time of the flight",
            minLength = 10, maxLength = 75)
    ZonedDateTime arrivalTime;

    @NotNull
    @Schema(example = "2021-10-20T17:11:44.074334Z", description = "V2 Departure Time of the flight",
            minLength = 10, maxLength = 75)
    ZonedDateTime departureTime;

    @NotNull
    @Min(1)
    @Max(Integer.MAX_VALUE)
    @Schema(description = "V2 Flight Price")
    Long price;

    @Schema(description = "V2 Flight Delay Flag")
    Boolean delay;
}
