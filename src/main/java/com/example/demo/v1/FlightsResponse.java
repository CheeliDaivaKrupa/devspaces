package com.example.demo.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Flights Response that wraps the list of Flight APIs")
public class FlightsResponse {

    @Size(max = 999)
    @Schema(description = "List of Flight APIs")
    List<FlightApi> flights;
}
