import java.io.*;
import java.util.*;

public class Admin {
    private static final String ADMIN_EMAIL = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public static boolean login(String email, String password) {
        return ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD.equals(password);
    }

    public static String addFlight(String flightNumber, String destination, String date, String time, int seats, boolean hasCustomTimeLimitForBooking, int customBookingTimeLimit) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("flights.txt", true))) {
            writer.write(flightNumber + "," + destination + "," + date + "," + time + "," + seats + "," + hasCustomTimeLimitForBooking + "," + customBookingTimeLimit);
            writer.newLine();
        }
        System.out.println("Flight added successfully!");
        return "Flight added successfully!";
    }

    public static String updateFlight(String flightNumber, String newDestination, String newDate, String newTime, int newSeats, boolean hasNewCustomTimeLimitForBooking, int customBookingTimeLimit) throws IOException {
        File file = new File("flights.txt");
        File tempFile = new File("flights_temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(flightNumber)) {
                    writer.write(flightNumber + "," + newDestination + "," + newDate + "," + newTime + "," + newSeats + "," + hasNewCustomTimeLimitForBooking + "," + customBookingTimeLimit);
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        }
        file.delete();
        tempFile.renameTo(file);
        System.out.println("Flight updated successfully!");
        return "Flight updated successfully!";
    }
}
