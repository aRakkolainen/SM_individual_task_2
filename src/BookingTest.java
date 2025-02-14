import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

class BookingTest {

    @Test
    public void testOverBookFlight() throws IOException {
        //Source: https://stackoverflow.com/questions/32241057/how-to-test-a-print-method-in-java-using-junit
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Booking.bookFlight("habib", "717", 38);
        Assertions.assertEquals("Not enough seats available.", outContent.toString());
    }

    @Test
    void testCancelBooking() {
        //Booking.cancelBooking("email", "flightNumber");
    }

    @Test
    void testViewBookingHistory() {
        //Booking.viewBookingHistory("email");
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme