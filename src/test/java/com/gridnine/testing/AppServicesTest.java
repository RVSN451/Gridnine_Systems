package com.gridnine.testing;

import com.gridnine.testing.model.AppServices;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AppServicesTest {

    private final static LocalDateTime THREE_DAYS_FROM_NOW = LocalDateTime.now().plusDays(3);

    private static final List<Segment> NORMAL_FLIGHT_WITH_TWO_HOUR_DURATION_SEGMENTS = List.of(
            new Segment(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2)));
    private static final List<Segment> NORMAL_MULTI_SEGMENT_FLIGHT_SEGMENTS = Arrays.asList(
            new Segment(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.plusHours(2)),
            new Segment(THREE_DAYS_FROM_NOW.plusHours(3), THREE_DAYS_FROM_NOW.plusHours(5)));
    private static final List<Segment> FLIGHT_DEPARTING_IN_THE_PAST_SEGMENTS = List.of(
            new Segment(THREE_DAYS_FROM_NOW.minusDays(6), THREE_DAYS_FROM_NOW));
    private static final List<Segment> FLIGHT_DEPARTS_AFTER_IT_ARRIVES_SEGMENTS = List.of(
            new Segment(THREE_DAYS_FROM_NOW, THREE_DAYS_FROM_NOW.minusHours(6)));


    private final static List<Flight> FLIGHT_LIST = Arrays.asList(
            new Flight(NORMAL_FLIGHT_WITH_TWO_HOUR_DURATION_SEGMENTS),
            new Flight(NORMAL_MULTI_SEGMENT_FLIGHT_SEGMENTS),
            new Flight(FLIGHT_DEPARTING_IN_THE_PAST_SEGMENTS),
            new Flight(FLIGHT_DEPARTS_AFTER_IT_ARRIVES_SEGMENTS));

    @Test
    void departureUntilSpecifiedTimeTest() {
        List<Flight> flightList = new ArrayList<>(FLIGHT_LIST);
        assertEquals(3, AppServices.departureUntilSpecifiedTime(flightList, LocalDateTime.now()).size());
    }

    @Test
    void waitingTimeFilterTest() {
        List<Flight> flightList = new ArrayList<>(FLIGHT_LIST);
        flightList.remove(1);
        Flight expected = flightList.get(1);
        Flight actual = AppServices.waitingTimeFilter(new ArrayList<>(FLIGHT_LIST), 15, ChronoUnit.MINUTES).get(1);

        assertTrue(expected.equals(actual));
    }

    @Test
    void arrivalBeforeDepartureTest() {
        List<Flight> flightList = new ArrayList<>(FLIGHT_LIST);
        List<Flight> expected = new ArrayList<>(FLIGHT_LIST);
        expected.remove(3);
        List<Flight> actual = AppServices.arrivalBeforeDeparture(flightList);

        assertEquals(expected.toString(),
                actual.toString());
    }
}

