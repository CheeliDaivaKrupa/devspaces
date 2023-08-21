package com.example.demo.flight.api.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Flight V2 Response that wraps the Flight V2 API")
public class FlightV2Response {

    FlightV2Api flight;
}
