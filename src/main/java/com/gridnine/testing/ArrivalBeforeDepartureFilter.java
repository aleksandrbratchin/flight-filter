package com.gridnine.testing;

import java.util.List;

/**
 * Класс ArrivalBeforeDepartureFilter реализует интерфейс FlightFilter и предназначен для фильтрации полетов,
 * у которых все сегменты имеют корректное время прибытия, то есть время прибытия позже времени вылета.
 */
public class ArrivalBeforeDepartureFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .toList();
    }
}
