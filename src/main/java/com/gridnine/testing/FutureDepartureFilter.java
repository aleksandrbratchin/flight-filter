package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс FutureDepartureFilter реализует интерфейс FlightFilter и предназначен для фильтрации полетов,
 * у которых все сегменты вылетают после текущего момента времени.
 */
public class FutureDepartureFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        LocalDateTime currentTime = LocalDateTime.now();
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(currentTime)))
                .toList();
    }

}
