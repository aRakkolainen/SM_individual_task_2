import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

class AdminTest {
    private static String testUserEmail = "habib";
    //Test cases for admin login with correct and incorrect credentials
    @Test
    void testLoginWithCorrectCredentials() {
        boolean result = Admin.login("admin", "admin");
        Assertions.assertTrue(result);
    }

    @Test
    void testLoginWithIncorrectCredentials() {
        boolean result = Admin.login("admin", "1234");
        Assertions.assertFalse(result);
    }

    @Test
    void testAddFlight() throws IOException {
        Assertions.assertEquals("Flight added successfully!", Admin.addFlight("717","Dhaka","12.01.25","6.00 pm",98, false, 5));
    }


    /**This is an unit test for testing updateFlight functionality when existing flight 717 which currently has 98 seats, will be added 10 more seats
     * and other values remain the same*/
    @Test
    void testUpdateFlight() throws IOException {
        List<String> oldFlight = Flight.searchFlights("flightNumber", "717");
        String expected = "Flight updated successfully!";
        String oldFlightStr = oldFlight.getFirst();
        String[] flight = oldFlightStr.split(",");
        String destination = flight[1];
        String date = flight[2];
        String time = flight[3];
        boolean hasCustomBookingTimeLimit = Boolean.parseBoolean(flight[5]);
        int timeLimit = Integer.parseInt(flight[6]);
        int oldSeats = Integer.parseInt(flight[4]);
        Assertions.assertEquals(expected, Admin.updateFlight("717", destination, date, time, oldSeats+10, hasCustomBookingTimeLimit, timeLimit));
    }
    /**This is one integration test case for checking that when Admin adds new Flight, it is also found by the Flight class which uses it
     * when user books a new flight and then the user also cancels this flight. Idea is to test the whole process related to Flight class.  */
    @Test
    void testAdminAddsFlightAndFlightIsFoundForNewBooking() throws IOException {
        Assertions.assertEquals("Flight added successfully!", Admin.addFlight("720", "Helsinki", "25.02.25", "3.00 pm", 100, true, 5));
        String expectedBeforeBooking = "720,Helsinki,25.02.25,3.00 pm,100,true,5";
        List<String> resultBeforeBooking = Flight.searchFlights("destination", "Helsinki");
        Assertions.assertTrue(resultBeforeBooking.contains(expectedBeforeBooking));
        Assertions.assertEquals("Booking successful!", Booking.bookFlight(testUserEmail, "720", 2));
        String expectedAfterBooking = "720,Helsinki,25.02.25,3.00 pm,98,true,5";
        List<String> resultAfterBooking = Flight.viewFlights();
        Assertions.assertTrue(resultAfterBooking.contains(expectedAfterBooking));
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme