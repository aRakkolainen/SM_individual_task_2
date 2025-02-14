import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class AdminTest {

    @Test
    void testLogin() {
        //boolean result = Admin.login("email", "password");
        //Assertions.assertEquals(true, result);
    }

    @Test
    void testAddFlight() {
        //Assert.assertThrows(Admin.addFlight("flightNumber", "destination", "date", "time", 0), IOException.class);
    }

    @Test
    void testUpdateFlight() {
        try{
            Admin.updateFlight("flightNumber", "newDestination", "newDate", "newTime", 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**This is one integration test case for checking that when Admin adds new Flight, it is also found by the Flight class which uses it*/
    @Test
    void testAdminAddsFlightAndFlightIsFound() throws IOException {
        try {
            Admin.addFlight("720", "Finland", "15.02.25", "3.00 pm", 100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String expected = "720,Finland,15.02.25,3.00 pm,100";
        List<String> result = Flight.searchFlights("destination", "Finland");
        Assertions.assertEquals(List.of(expected), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme