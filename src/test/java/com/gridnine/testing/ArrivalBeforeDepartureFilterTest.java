package com.gridnine.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ArrivalBeforeDepartureFilterTest {

    private ArrivalBeforeDepartureFilter filter;

    @BeforeEach
    void setUp() {
        filter = new ArrivalBeforeDepartureFilter();
    }

    @Test
    void testFilterAllValidFlights() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        Flight flight1 = new Flight(
                List.of(
                        new Segment(now.plusDays(1), now.plusDays(2)),
                        new Segment(now.plusDays(3), now.plusDays(4))
                )
        );
        Flight flight2 = new Flight(
                List.of(
                        new Segment(now.plusHours(5), now.plusHours(6))
                )
        );
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filter(flights);

        assertThat(result).containsExactlyInAnyOrderElementsOf(flights);
    }

    @Test
    void testFilterSomeInvalidFlights() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        Flight flight1 = new Flight(
                List.of(
                        new Segment(now.plusDays(2), now.plusDays(1))
                ));
        Flight flight2 = new Flight(
                List.of(
                        new Segment(now.plusHours(5), now.plusHours(6))
                ));
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filter(flights);

        assertThat(result).containsExactly(flight2);
    }

    @Test
    void testFilterAllInvalidFlights() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        Flight flight1 = new Flight(
                List.of(
                        new Segment(now.plusDays(2), now.plusDays(1))
                ));
        Flight flight2 = new Flight(
                List.of(
                        new Segment(now.plusHours(6), now.plusHours(5))
                ));
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filter(flights);

        assertThat(result).isEmpty();
    }

    @Test
    void testFilterEmptyFlightsList() {
        List<Flight> flights = Collections.emptyList();

        List<Flight> result = filter.filter(flights);

        assertThat(result).isEmpty();
    }

    @Test
    void testFilterFlightWithMixedSegments() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        Flight flight1 = new Flight(
                List.of(
                        new Segment(now.plusDays(1), now.plusDays(2)),
                        new Segment(now.plusDays(3), now.plusDays(1))
                )
        );
        Flight flight2 = new Flight(
                List.of(
                        new Segment(now.plusHours(5), now.plusHours(6))
                )
        );
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filter(flights);

        assertThat(result).containsExactly(flight2);
    }

}