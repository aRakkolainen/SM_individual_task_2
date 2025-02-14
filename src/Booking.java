import java.io.*;
import java.util.*;

public class Booking {
    private static final String BOOKING_FILE = "bookings.txt";

    public static void bookFlight(String email, String flightNumber, int seats) throws IOException {
        File flightFile = new File("flights.txt");
        File tempFile = new File("flights_temp.txt");
        boolean flightFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(flightFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(flightNumber)) {
                    flightFound = true;
                    int availableSeats = Integer.parseInt(details[4]);
                    if (availableSeats >= seats) {
                        availableSeats -= seats;
                        writer.write(details[0] + "," + details[1] + "," + details[2] + "," + details[3] + "," + availableSeats);
                        writer.newLine();

                        try (BufferedWriter bookingWriter = new BufferedWriter(new FileWriter(BOOKING_FILE, true))) {
                            bookingWriter.write(email + "," + flightNumber + "," + seats);
                            bookingWriter.newLine();
                        }
                        System.out.println("Booking successful!");
                    } else {
                        System.out.println("Not enough seats available.");
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
            System.out.println("Flight not found.");
        }
    }

    public static void cancelBooking(String email, String flightNumber) throws IOException {
        File bookingFile = new File(BOOKING_FILE);
        File tempFile = new File("bookings_temp.txt");
        boolean bookingFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(bookingFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(email) && details[1].equals(flightNumber)) {
                    bookingFound = true;
                    updateSeats(flightNumber, Integer.parseInt(details[2]));
                    System.out.println("Booking canceled successfully!");
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
        }
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
                    writer.write(details[0] + "," + details[1] + "," + details[2] + "," + details[3] + "," + availableSeats);
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        }

        flightFile.delete();
        tempFile.renameTo(flightFile);
    }

    public static void viewBookingHistory(String email) throws IOException {
        File bookingFile = new File(BOOKING_FILE);

        System.out.println("\nBooking History for " + email + ":");
        try (BufferedReader reader = new BufferedReader(new FileReader(bookingFile))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(email)) {
                    found = true;
                    System.out.println("Flight Number: " + details[1] + ", Seats Booked: " + details[2]);
                }
            }

            if (!found) {
                System.out.println("No bookings found.");
            }
        }
    }
}
