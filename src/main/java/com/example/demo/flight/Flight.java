package com.example.demo.flight;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cab_ford_air_flight")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "flight_number")
    String flightNumber;

    @Column(name = "airline")
    String airline;

    @Column(name = "arrival_time")
    OffsetDateTime arrivalTime;

    @Column(name = "departure_time")
    OffsetDateTime departureTime;

    @Column(name = "price")
    Long price;

    @Column(name = "delay")
    Boolean delay;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @Version
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;
	
    // Lombok uses get and set for Boolean, while is uses is and set boolean.
    // Supporting "is" method here with Null safety in mind.
    public boolean isDelay() {
        return Boolean.TRUE.equals(delay);
    }

}
