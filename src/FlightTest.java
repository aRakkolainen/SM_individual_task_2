import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class FlightTest {

    @Test
    void testViewFlights() throws IOException {
        List<String> result = Flight.viewFlights();
        Assertions.assertEquals(List.of("717,Dhaka,12.01.25,6.00 pm,68"), result);
    }

    @Test
    void testSearchFlights() throws IOException {
        List<String> result = Flight.searchFlights("destination", "Dhaka");
        Assertions.assertEquals(List.of("717,Dhaka,12.01.25,6.00 pm,68"), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme