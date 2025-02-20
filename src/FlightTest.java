import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FlightTest {
    /**Unit test for testing that one test flight is found */
    @Test
    void testSearchFlightsByDestination() throws IOException {
        List<String> expected = new ArrayList<>();
        expected.add("700,Berlin,18.02.25,8.00 am,70,true,10");
        List<String> result = Flight.searchFlights("destination", "Berlin");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testSearchFlightsByDestinationNotFound() throws IOException {
        List<String> expected = new ArrayList<>();
        List<String> result = Flight.searchFlights("destination", "London");
        Assertions.assertEquals(expected, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme