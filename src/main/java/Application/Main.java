package Application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        ConcurrentHashMap<String, Integer> sharedMap = new ConcurrentHashMap<String,Integer>();

        Path path1 = Path.of(Objects.requireNonNull(Main.class.getResource("/server1.log")).toURI());
        Path path2 = Path.of(Objects.requireNonNull(Main.class.getResource("/server2.log")).toURI());

        List<String> allContent1 = Files.readAllLines(path1, StandardCharsets.UTF_8);
        List<String> allContent2 = Files.readAllLines(path2, StandardCharsets.UTF_8);

        System.out.println(CountLogWords(allContent1, sharedMap));
    }

    private static ConcurrentHashMap<String, Integer> CountLogWords(List<String> allContent1, ConcurrentHashMap<String, Integer> sharedMap) {
        for (String line : allContent1)
        {
            String[] words = line.split("\\W+");
            for (String word : words) {
                if (word.equals("WARNING") || word.equals("SEVERE") || word.equals("LOG")) {
                    sharedMap.put(word, sharedMap.getOrDefault(word, 0) + 1);
                }
            }
        }
        return sharedMap;
    }
}