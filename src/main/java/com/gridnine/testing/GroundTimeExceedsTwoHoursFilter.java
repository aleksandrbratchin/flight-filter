package com.gridnine.testing;

import java.time.Duration;
import java.util.List;

/**
 * Класс GroundTimeExceedsTwoHoursFilter реализует интерфейс FlightFilter и предназначен для фильтрации полетов,
 * у которых время на земле между всеми сегментами не превышает двух часов.
 */
public class GroundTimeExceedsTwoHoursFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    long totalGroundTime = calculateTotalGroundTime(segments);
                    return totalGroundTime <= 2;
                })
                .toList();
    }

    private long calculateTotalGroundTime(List<Segment> segments) {
        long totalGroundTime = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            Segment current = segments.get(i);
            Segment next = segments.get(i + 1);
            long groundTimeBetweenSegments = Duration.between(
                    current.getArrivalDate(),
                    next.getDepartureDate()
            ).toHours();
            totalGroundTime += groundTimeBetweenSegments;
        }
        return totalGroundTime;
    }
}
