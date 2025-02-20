import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


public class BookingTest {
    private static String testUserEmail = "tester@email.com";
    private static final String testFlightNumber = "711";
    private static final String testFlightNumberPast = "700";

    @Test
    public void testBookFlight() throws IOException {
        String expected = "Booking successful!";
        Assertions.assertEquals(expected, Booking.bookFlight(testUserEmail, testFlightNumber, 2));
    }


    @Test
    void testCancelBooking() throws IOException {
        String expected = "Booking canceled successfully!";
        Booking.bookFlight(testUserEmail, testFlightNumber, 2);
        Assertions.assertEquals(expected, Booking.cancelBooking(testUserEmail, testFlightNumber));
    }

    /**This is unit test for case where the user tries to book more seats on the flight than there are available (or this actually integration test..)*/
    @Test
    public void testOverBookingFlight() throws IOException {
        List<String> flight =  Flight.searchFlights("flightNumber", "717");
        String flightData = flight.getFirst();
        String[] flightInfo = flightData.split(",");
        int flightSeatsMax = Integer.parseInt(flightInfo[4]);
        int seatsToBeReserved = flightSeatsMax + 2; //Tries to book 2 more than maximum.
        Assertions.assertEquals("Not enough seats available.", Booking.bookFlight("habib", testFlightNumber, seatsToBeReserved));
        List<String> flightNew = Flight.searchFlights("flightNumber", "717");
        Assertions.assertEquals(flight, flightNew);
        String expected = "No bookings found.";
        String actual = Booking.viewBookingHistory(testUserEmail);
        Assertions.assertEquals(expected, actual);
    }


    //Additional test cases for testing the new feature

    @Test
    public void testBookingFlightWhenTimeLimitHasPassedMethod() throws IOException {
        List<String> flight =  Flight.searchFlights("flightNumber", "717");
        String flightData = flight.getFirst();
        String[] flightDetails = flightData.split(",");
        LocalDate bookingDate = LocalDate.now();
        LocalTime bookingTime = LocalTime.now();
        Booking.checkIfBookingTimeLimitHasPassed(flightDetails,bookingDate, bookingTime);
        Assertions.assertTrue(Booking.checkIfBookingTimeLimitHasPassed(flightDetails,bookingDate, bookingTime));
    }

    @Test
    public void testBookingFlightOfThePastMonth() throws IOException {
        String expected = "Booking cannot be added because the flight departs less than 5 hour(s) or the flight has already departed!";
        Assertions.assertEquals(expected, Booking.bookFlight(testUserEmail, "717", 2));
    }
    @Test
    public void testBookingFlightFromThisMorning() throws IOException{
        int bookingTimeLimit = 10;
        String expected = "Booking cannot be added because the flight departs less than " + bookingTimeLimit +  " hour(s) or the flight has already departed!";
        Assertions.assertEquals(expected, Booking.bookFlight(testUserEmail, testFlightNumberPast, 2));
    }

}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme