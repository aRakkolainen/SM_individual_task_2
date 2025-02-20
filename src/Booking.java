import org.junit.jupiter.api.DisplayNameGenerator;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Booking {
    private static final String BOOKING_FILE = "bookings.txt";

    public static String bookFlight(String email, String flightNumber, int seats) throws IOException {
        File flightFile = new File("flights.txt");
        File tempFile = new File("flights_temp.txt");
        boolean flightFound = false;
        boolean bookingTimeLimitHasPassed = false;
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        String resultString = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(flightFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(flightNumber)) {
                    bookingTimeLimitHasPassed = checkIfBookingTimeLimitHasPassed(details, currentDate, currentTime);
                    flightFound = true;
                    //New feature for checking the booking date and time limit
                    int availableSeats = Integer.parseInt(details[4]);
                    if (!bookingTimeLimitHasPassed) {
                        if (availableSeats >= seats) {
                            availableSeats -= seats;
                            writer.write(details[0] + "," + details[1] + "," + details[2] + "," + details[3] + "," + availableSeats + "," + details[5] + "," + details[6]);
                            writer.newLine();

                            try (BufferedWriter bookingWriter = new BufferedWriter(new FileWriter(BOOKING_FILE, true))) {
                                bookingWriter.write(email + "," + flightNumber + "," + seats);
                                bookingWriter.newLine();
                            }
                            resultString = "Booking successful!";
                            System.out.println(resultString);
                        } else {
                            resultString = "Not enough seats available."; //Added for testing
                            System.out.println(resultString);
                            writer.write(line);
                            writer.newLine();
                        }
                    } else {
                        resultString = "Booking cannot be added because the flight departs less than " + details[6] + " hour(s) or the flight has already departed!";
                        System.out.println(resultString);
                        writer.write(line);
                        writer.newLine();
                    }
                } else {
                    writer.write(line);
                    writer.newLine();
                }
                }
        }

        flightFile.delete();
        tempFile.renameTo(flightFile);

        if (!flightFound) {
            resultString = "Flight not found!";
            System.out.println(resultString);
        }
        return resultString;
    }

    public static String cancelBooking(String email, String flightNumber) throws IOException {
        File bookingFile = new File(BOOKING_FILE);
        File tempFile = new File("bookings_temp.txt");
        boolean bookingFound = false;
        String resultString = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(bookingFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(email) && details[1].equals(flightNumber)) {
                    bookingFound = true;
                        updateSeats(flightNumber, Integer.parseInt(details[2]));
                        resultString = "Booking canceled successfully!";
                        System.out.println(resultString);
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }

        bookingFile.delete();
        tempFile.renameTo(bookingFile);

        if (!bookingFound) {
            System.out.println("Booking not found.");
            resultString = "Booking not found.";
        }
        return resultString;
    }

    private static void updateSeats(String flightNumber, int seats) throws IOException {
        File flightFile = new File("flights.txt");
        File tempFile = new File("flights_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(flightFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(flightNumber)) {
                    int availableSeats = Integer.parseInt(details[4]);
                    availableSeats += seats;
                    writer.write(details[0] + "," + details[1] + "," + details[2] + "," + details[3] + "," + availableSeats + "," + details[5] + "," + details[6]);
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        }

        flightFile.delete();
        tempFile.renameTo(flightFile);
    }

    public static String viewBookingHistory(String email) throws IOException {
        File bookingFile = new File(BOOKING_FILE);
        String resultString = "";
        System.out.println("\nBooking History for " + email + ":");
        try (BufferedReader reader = new BufferedReader(new FileReader(bookingFile))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(email)) {
                    found = true;
                    resultString = "Flight Number: " + details[1] + ", Seats Booked: " + details[2];
                    System.out.println(resultString);
                }
            }

            if (!found) {
                resultString = "No bookings found.";
                System.out.println(resultString);
            }
        }
        return resultString;
    }

    /**This is the new feature for limiting booking that if the user's booking date and time is less than
     * three hours before the time flight leaves, it is not possible to make a new booking (or update booking).
     * The idea for this new feature came from previous course where we gathered possible requirements for meal preordering
     * service system for Finnair's flights and there was set time limit for making preorders.
     * Probably it could be useful in reality that the last-minute bookings are not possible for regular passengers,
     * it could be different case that the employee can make booking at the gate even if there is less time left
     * but in general, this feature could help with cabin crew planning and to expect how many passengers should arrive
     */

    public static boolean checkIfBookingTimeLimitHasPassed(String[] flightDetails, LocalDate bookingDate, LocalTime bookingTime) {
        boolean bookingTimeLimitHasPassed = false;
        String flightNumber = flightDetails[0];
        String flightDate = flightDetails[2];
        String flightTime = flightDetails[3];
        int bookingTimeLimit = 5;
        if (flightDetails.length == 7){
            bookingTimeLimit = Integer.parseInt(flightDetails[6]);
        }
        // Microsoft Copilot was used to explain the error that leaded to this fix: (It required changing the time format in flights.txt to fit this purpose
        String correctFlightTimeFormat = flightTime.replace(".", ":").toUpperCase();
        String bookingDateStr = bookingDate.format(DateTimeFormatter.ofPattern("dd.MM.yy"));
        String time = LocalTime.parse(correctFlightTimeFormat, DateTimeFormatter.ofPattern("h:mm a", Locale.US)).format(DateTimeFormatter.ofPattern("HH:mm"));



        //Converting 12-hour clock to 24-hour format because my desktop uses that format based on this article: https://www.baeldung.com/java-convert-time-format
        LocalDate flightDepartureDate = LocalDate.parse(flightDate, DateTimeFormatter.ofPattern("dd.MM.yy"));
        LocalTime flightDepartureTime = LocalTime.parse(time);

        //checking if the flight departure date has already passed before the booking date
        if (bookingDate.isAfter(flightDepartureDate)) {
            bookingTimeLimitHasPassed = true;
        }
        //checking if the flight date is the same as current date because then it needs to be checked at hour level and take into account the custom time limit
        if (flightDepartureDate.equals(bookingDate)){
            System.out.println("Booking date is the same as the flight departure day so it needs to be checked if flight has already departed or is leaving in less than" + bookingTimeLimit + " hours.");
            if(bookingTime.isAfter(flightDepartureTime) || bookingTime.plusHours(bookingTimeLimit).isAfter(flightDepartureTime)){
                System.out.println("The booking time limit has passed!");
                bookingTimeLimitHasPassed = true;
            }
            System.out.println("Booking time limit has not passed so booking is possible!");

        }
        return bookingTimeLimitHasPassed;
    }
}
