package com.example.demo.flight.api.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Flights V2 Response that wraps the list of Flight V2 APIs")
public class FlightsV2Response {

    @Size(max = 999)
    @Schema(description = "List of Flight V2 APIs")
    List<FlightV2Api> flights;
}
