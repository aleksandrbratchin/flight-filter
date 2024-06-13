package com.gridnine.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GroundTimeExceedsTwoHoursFilterTest {

    private GroundTimeExceedsTwoHoursFilter filter;

    @BeforeEach
    void setUp() {
        filter = new GroundTimeExceedsTwoHoursFilter();
    }

    @Test
    void testFilterAllFlightsWithinLimit() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        Flight flight1 = new Flight(
                List.of(
                        new Segment(now.plusHours(1), now.plusHours(2)),
                        new Segment(now.plusHours(3), now.plusHours(4))
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
    void testFilterSomeFlightsExceedingLimit() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        Flight flight1 = new Flight(
                List.of(
                        new Segment(now.plusHours(1), now.plusHours(2)),
                        new Segment(now.plusHours(5), now.plusHours(6))
                )
        );
        Flight flight2 = new Flight(
                List.of(
                        new Segment(now.plusHours(1), now.plusHours(2)),
                        new Segment(now.plusHours(4), now.plusHours(5))
                )
        );
        List<Flight> flights = Arrays.asList(flight1, flight2);

        List<Flight> result = filter.filter(flights);

        assertThat(result).containsExactly(flight2);
    }

    @Test
    void testFilterAllFlightsExceedingLimit() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        Flight flight1 = new Flight(
                List.of(
                        new Segment(now.plusHours(1), now.plusHours(2)),
                        new Segment(now.plusHours(5).plusMinutes(1), now.plusHours(7))
                )
        );
        Flight flight2 = new Flight(
                List.of(
                        new Segment(now.plusHours(1), now.plusHours(2)),
                        new Segment(now.plusHours(3), now.plusHours(5)),
                        new Segment(now.plusHours(7), now.plusHours(8))
                )
        );
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
    void testFilterSingleSegmentFlight() {
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0);
        Flight flight = new Flight(List.of(
                new Segment(now.plusHours(1), now.plusHours(2))
        ));
        List<Flight> flights = Collections.singletonList(flight);

        List<Flight> result = filter.filter(flights);

        assertThat(result).containsExactly(flight);
    }

}