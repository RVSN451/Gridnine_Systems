package com.gridnine.testing.model;

import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class AppServices {
    public static List<Flight> arrivalBeforeDeparture(List<Flight> flightList) {
        return flightList.stream()
                .filter(flight ->
                        flight.getSegments().stream()
                                .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    public static List<Flight> departureUntilSpecifiedTime(List<Flight> flightList, ChronoLocalDateTime specifiedTime) {
        return flightList.stream()
                .filter(flight -> flight.getSegments().get(0).getDepartureDate().isAfter(specifiedTime))
                .collect(Collectors.toList());
    }

    public static List<Flight> waitingTimeFilter
            (List<Flight> flightList, long unitOfTime, ChronoUnit chronoUnit) {
        return flightList.stream()
                .filter(flight -> {
                    long waitingTime = 0;
                    for (int i = 0; i < flight.getSegments().size() - 1; i++) {
                        waitingTime += flight.getSegments().get(i).getArrivalDate().until(
                                flight.getSegments().get(i + 1).getDepartureDate(), chronoUnit);
                        if (waitingTime > unitOfTime) break;
                    }

                    return waitingTime < unitOfTime;
                })
                .collect(Collectors.toList());
    }
}
