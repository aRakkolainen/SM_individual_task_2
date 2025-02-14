import java.io.*;
import java.util.*;

public class Flight {
    private static final String FLIGHT_FILE = "flights.txt";

    public static List<String> viewFlights() throws IOException {
        List<String> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FLIGHT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flights.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }

    public static List<String> searchFlights(String searchKey, String searchValue) throws IOException {
        List<String> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FLIGHT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if ((searchKey.equals("destination") && details[1].equals(searchValue)) ||
                        (searchKey.equals("date") && details[2].equals(searchValue)) ||
                        (searchKey.equals("flightNumber") && details[0].equals(searchValue))) {
                    results.add(line);
                }
            }
        }
        return results;
    }
}
