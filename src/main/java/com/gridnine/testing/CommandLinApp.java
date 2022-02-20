package com.gridnine.testing;

import com.gridnine.testing.model.AppServices;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.FlightBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Component
public class CommandLinApp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        List<Flight> flightList = new ArrayList<>(FlightBuilder.createFlights());

        System.out.println("flightList: " + "\n" + flightList);

        System.out.println("departureUntilCurrentTimeList: " + "\n" +
                AppServices.departureUntilSpecifiedTime(flightList, LocalDateTime.now()));

        System.out.println("arrivalBeforeDeparture: " + "\n" +
                AppServices.arrivalBeforeDeparture(flightList));

        System.out.println("waitingTimeFilter (> 2 HOURS): " + "\n" +
                AppServices.waitingTimeFilter(flightList, 2L, ChronoUnit.HOURS));

    }
}
