package nl.michielgraat.adventofcode2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DailySetup {

    public static void setupDay() throws IOException {
        setupDay(getDay());
    }

    public static void setupDay(int day) throws IOException {
        downloadInput(day);
        createSrc(day);
    }

    private static void createSrc(int day) throws IOException {
        Path dirPath = Paths.get("src", "nl", "michielgraat", "adventofcode2023", getSrcDirectory(day));
        Path filePath = Paths.get("src", "nl", "michielgraat", "adventofcode2023", getSrcDirectory(day), getSrcFile(day));
        Path skelPath = Paths.get("src", "nl", "michielgraat", "adventofcode2023", "skeleton", "Skeleton.java");
        if (!dirPath.toFile().exists()) {
            Files.createDirectories(dirPath);
        } 
        if (!filePath.toFile().exists()) {
            Files.copy(skelPath, filePath);
        }
        String content = new String(Files.readAllBytes(filePath));
        content = content.replaceAll("skeleton", "day" + getDayAsString(day));
        content = content.replaceAll("Skeleton", "Day" + getDayAsString(day));
        Files.write(filePath, content.getBytes());
    }

    private static void downloadInput(int day) throws IOException {
        List<String> configuration = getConfig();
        String emailAddress = configuration.get(0);
        String sessionId = configuration.get(1);
        if (!inputExists(day)) {
            long secsSinceLastRequest = getSecsSinceLastRequest();
            if (secsSinceLastRequest >= 300L) {
                List<String> input = getInput(day, sessionId, emailAddress);
                writeInputToFile(day, input);
            } else {
                System.out.println("It has not yet been 5 minutes since the last request to AOC servers, please wait "
                        + (300 - secsSinceLastRequest) + " seconds before trying again.");
            }
        } else {
            System.out.println("The file '" + getInputFilename(day)
                    + "' already exists in the 'resources' directory, so it will not be downloaded. Please remove it to download it again.");
        }
    }

    private static long getSecsSinceLastRequest() throws IOException {
        Path path = Paths.get("resources", "last_request.txt");
        if (path.toFile().exists() && path.toFile().isFile()) {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime fileTime = attr.creationTime();
            Instant time = fileTime.toInstant();
            Instant now = Instant.now();
            return ChronoUnit.SECONDS.between(time, now);
        } else {
            Files.createFile(path);
            return Long.MAX_VALUE;
        }
    }

    private static void writeInputToFile(int day, List<String> input) throws IOException {
        String filename = getInputFilename(day);
        Path path = Paths.get("resources", filename);
        Files.createFile(path);
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if (i < input.size() - 1) {
                line += System.lineSeparator();
            }
            Files.writeString(path, line, StandardOpenOption.APPEND);
        }
    }

    private static boolean inputExists(int day) {
        String filename = getInputFilename(day);
        Path p = Paths.get("resources", filename);
        return p.toFile().exists() && p.toFile().isFile();
    }

    private static String getInputFilename(int day) {
        return "day" + getDayAsString(day) + ".txt";
    }

    private static String getSrcDirectory(int day) {
        return "day" + getDayAsString(day);
    }

    private static String getSrcFile(int day) {
        return "Day" + getDayAsString(day) + ".java";
    }

    private static String getDayAsString(int day) {
        return (day < 10 ? "0" : "") + day;
    }

    private static List<String> getInput(int day, String sessionId, String emailAddress) throws IOException {
        BufferedReader in = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL("https://adventofcode.com/2023/day/" + day + "/input");

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Cookie", "session=" + sessionId);
            // Set up user-agent as expected by AOC.
            con.setRequestProperty("User-Agent", "github.com/ash42/adventofcode by " + emailAddress);

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return in.lines().collect(Collectors.toList());
        } finally {
            if (in != null) {
                in.close();
            }
            if (con != null) {
                con.disconnect();
            }
        }

    }

    private static int getDay() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        if (month != Calendar.DECEMBER) {
            throw new IllegalArgumentException(
                    "We are not in december, please provide day and year of the input to get");
        }
        return c.get(Calendar.DAY_OF_MONTH);
    }

    private static List<String> getConfig() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("resources", "config.txt"))) {
            List<String> content = stream.collect(Collectors.toList());
            if (content.size() != 2) {
                throw new IOException(
                        "The file config.txt does not have the correct format. The expected format is an email address on the first line and the session id on the second line");
            }
            return content;
        } catch (final IOException e) {
            throw new IOException(
                    "Could not read configuration, please make sure it is in a file called 'config.txt' in the resources directory.",
                    e);
        }
    }

    public static void main(String... args) {
        try {
            DailySetup.setupDay();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
