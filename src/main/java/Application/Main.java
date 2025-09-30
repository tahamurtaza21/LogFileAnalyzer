package Application;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, ExecutionException, InterruptedException {

        ConcurrentHashMap<String, Integer> sharedMap = new ConcurrentHashMap<String,Integer>();

//        Path path1 = Path.of(Objects.requireNonNull(Main.class.getResource("/biglog.txt")).toURI());
//        Path path2 = Path.of(Objects.requireNonNull(Main.class.getResource("/biglog2.txt")).toURI());
//
//        List<String> allContent1 = Files.readAllLines(path1, StandardCharsets.UTF_8);
//        List<String> allContent2 = Files.readAllLines(path2, StandardCharsets.UTF_8);
//
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//
//        Future<ConcurrentHashMap<String, Integer>> future1 = executor.submit(() -> CountLogWords(allContent1, sharedMap));
//        Future<ConcurrentHashMap<String, Integer>> future2 = executor.submit(() -> CountLogWords(allContent2, sharedMap));
//
//        long startTime = System.currentTimeMillis();
//        future1.get();
//        future2.get();
//        long endTime = System.currentTimeMillis();
//        System.out.println("Time taken: " + (endTime - startTime) + " ms");
//        System.out.println(sharedMap);
//        executor.shutdown();
//
//
//        long startTime1 = System.currentTimeMillis();
//        CountLogWords(allContent1, sharedMap);
//        System.out.println(CountLogWords(allContent2, sharedMap));
//        long endTime1 = System.currentTimeMillis();
//        System.out.println("Time taken: " + (endTime1 - startTime1) + " ms");


        BufferedImage img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("/logo.png")));

        getImage(img);
    }

    private static void getImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = img.getRGB(x, y);
                img.setRGB(x, y, pixel ^ 0x00ffffff);
            }
        }

        try {
            ImageIO.write(img, "png", new java.io.File("src/main/resources/output.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static ConcurrentHashMap<String, Integer> CountLogWords(List<String> allContent1, ConcurrentHashMap<String, Integer> sharedMap) {
        for (String line : allContent1)
        {
            String[] words = line.split("\\W+");
            for (String word : words) {
                if (word.equals("INFO") || word.equals("WARN") || word.equals("ERROR")) {
                    sharedMap.merge(word, 1, Integer::sum);
                }
            }
        }
        return sharedMap;
    }
}