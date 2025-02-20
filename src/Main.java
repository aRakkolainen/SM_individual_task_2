import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the Flight Management System");
            System.out.println("1. Register");
            System.out.println("2. Login as User");
            System.out.println("3. Login as Admin");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();
                    User.register(name, email, password);
                    break;
                case 2:
                    System.out.print("Enter your email: ");
                    String userEmail = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String userPassword = scanner.nextLine();
                    if (User.login(userEmail, userPassword)) {
                        userMenu(userEmail);
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case 3:
                    System.out.print("Enter admin email: ");
                    String adminEmail = scanner.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPassword = scanner.nextLine();
                    if (Admin.login(adminEmail, adminPassword)) {
                        adminMenu();
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void userMenu(String email) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. View Flights");
            System.out.println("2. Search Flights");
            System.out.println("3. Book Flight");
            System.out.println("4. Cancel Booking");
            System.out.println("5. View Bookings");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<String> flights = Flight.viewFlights();
                    flights.forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Enter search key (destination/date/flightNumber): ");
                    String key = scanner.nextLine();
                    System.out.print("Enter search value: ");
                    String value = scanner.nextLine();
                    List<String> results = Flight.searchFlights(key, value);
                    results.forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Enter flight number: ");
                    String flightNumber = scanner.nextLine();
                    System.out.print("Enter number of seats: ");
                    int seats = scanner.nextInt();
                    scanner.nextLine();
                    Booking.bookFlight(email, flightNumber, seats);
                    break;
                case 4:
                    System.out.print("Enter flight number: ");
                    String cancelFlightNumber = scanner.nextLine();
                    Booking.cancelBooking(email, cancelFlightNumber);
                    break;
                case 5:
                    Booking.viewBookingHistory(email);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }


    private static void adminMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Flight");
            System.out.println("2. Update Flight");
            System.out.println("3. View Flights");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter flight number: ");
                    String flightNumber = scanner.nextLine();
                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine();
                    System.out.print("Enter date: ");
                    String date = scanner.nextLine();
                    System.out.print("Enter time: ");
                    String time = scanner.nextLine();
                    System.out.print("Enter number of seats: ");
                    int seats = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter has limited time for booking (true/false): ");
                    boolean hasCustomTimeLimitForBooking = scanner.nextBoolean();
                    scanner.nextLine();
                    int customBookingTimeLimit = 5;//Default time limit is five hours but otherwise it can be customized by admin
                    if(hasCustomTimeLimitForBooking){
                        System.out.println("Enter the time limit for booking (0-24): ");
                        customBookingTimeLimit = scanner.nextInt();
                    }
                    scanner.nextLine();
                    Admin.addFlight(flightNumber, destination, date, time, seats, hasCustomTimeLimitForBooking, customBookingTimeLimit);
                    break;
                case 2:
                    System.out.print("Enter flight number: ");
                    String updateFlightNumber = scanner.nextLine();
                    System.out.print("Enter new destination: ");
                    String newDestination = scanner.nextLine();
                    System.out.print("Enter new date: ");
                    String newDate = scanner.nextLine();
                    System.out.print("Enter new time: ");
                    String newTime = scanner.nextLine();
                    System.out.print("Enter new number of seats: ");
                    int newSeats = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter has limited time for booking (true/false): ");
                    boolean newHasTimeLimitForBooking = scanner.nextBoolean();
                    scanner.nextLine();
                    System.out.println("Enter the time limit for booking (0-24): ");
                    int newBookingTimeLimit = scanner.nextInt();
                    scanner.nextLine();
                    Admin.updateFlight(updateFlightNumber, newDestination, newDate, newTime, newSeats, newHasTimeLimitForBooking, newBookingTimeLimit);
                    break;
                case 3:
                    List<String> flights = Flight.viewFlights();
                    flights.forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
