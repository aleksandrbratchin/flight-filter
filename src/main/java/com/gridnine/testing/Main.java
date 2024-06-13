package com.gridnine.testing;

import java.util.List;

public class Main
{
    public static void main( String[] args ) {
        FutureDepartureFilter futureDepartureFilter = new FutureDepartureFilter();
        ArrivalBeforeDepartureFilter arrivalBeforeDepartureFilter = new ArrivalBeforeDepartureFilter();
        GroundTimeExceedsTwoHoursFilter groundTimeExceedsTwoHoursFilter = new GroundTimeExceedsTwoHoursFilter();

        List<Flight> futureDeparture = new FlightFilterChain(FlightBuilder.createFlights())
                .addFilter(futureDepartureFilter)
                .filter();

        List<Flight> arrivalBeforeDeparture = new FlightFilterChain(FlightBuilder.createFlights())
                .addFilter(arrivalBeforeDepartureFilter)
                .filter();

        List<Flight> groundTimeExceeds = new FlightFilterChain(FlightBuilder.createFlights())
                .addFilter(groundTimeExceedsTwoHoursFilter)
                .filter();

        List<Flight> allFilters = new FlightFilterChain(FlightBuilder.createFlights())
                .addFilter(futureDepartureFilter)
                .addFilter(arrivalBeforeDepartureFilter)
                .addFilter(groundTimeExceedsTwoHoursFilter)
                .filter();

        System.out.println("Original Flights:");
        FlightBuilder.createFlights().forEach(System.out::println);

        System.out.println("\nFiltered Flights (Future Departure):");
        futureDeparture.forEach(System.out::println);

        System.out.println("\nFiltered Flights (Arrival before Departure):");
        arrivalBeforeDeparture.forEach(System.out::println);

        System.out.println("\nFiltered Flights (Ground Time Exceeds Two Hours):");
        groundTimeExceeds.forEach(System.out::println);

        System.out.println("\nFiltered Flights All:");
        allFilters.forEach(System.out::println);
    }

}
