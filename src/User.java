import java.io.*;
import java.util.*;

public class User {
    private static final String USER_FILE = "users.txt";

    public static void register(String name, String email, String password) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(name + "," + email + "," + password);
            writer.newLine();
        }
        System.out.println("User registered successfully!");
    }

    public static boolean login(String email, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[1].equals(email) && details[2].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }
}
