package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class LogGenerator {

    private static final String[] LEVELS = {"INFO", "WARN", "ERROR"};
    private static final String[] MESSAGES = {
            "User logged in",
            "User logged out",
            "Disk space low",
            "Database connection failed",
            "File not found",
            "Cache cleared",
            "Background job started",
            "Background job completed",
            "Unauthorized access attempt",
            "Configuration reloaded"
    };

    public static void main(String[] args) throws IOException {
        // generates 1 million lines
        generateLogFile("src/main/resources/biglog.txt", 1_000_000);
    }

    public static void generateLogFile(String path, int lines) throws IOException {
        Random rand = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < lines; i++) {
                String timestamp = LocalDateTime.now()
                        .minusSeconds(rand.nextInt(100_000)) // random past time
                        .format(formatter);

                String level = LEVELS[rand.nextInt(LEVELS.length)];
                String message = MESSAGES[rand.nextInt(MESSAGES.length)];

                writer.write("[" + timestamp + "] " + level + " " + message);
                writer.newLine();
            }
        }
    }
}
