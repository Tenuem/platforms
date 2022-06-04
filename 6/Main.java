import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.min;

public class Main {

    protected static Pair<String, BufferedImage> compute(Pair<String, BufferedImage> pair) {
        BufferedImage original = pair.getRight();
        BufferedImage outImage = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                int rgb = original.getRGB(i, j);
                Color color = new Color(rgb);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                Color outColor = new Color((float) (0.299 * (float)red)/255, (float) (0.587 * (float)green)/255,(float) (0.114 * (float)blue)/255);
                int outRgb = outColor.getRGB();
                outImage.setRGB(i, j, outRgb);
            }
        }

        return Pair.of(pair.getLeft(), outImage);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(args[0] + " " + args[1]);
        System.out.println("Started reading images!");
        List<Path> files = null;
        Path source = Path.of(args[0]);
        try (Stream<Path> stream = Files.list(source)) {
            files = stream.collect(Collectors.toList());
            System.out.println("Images count: " + files.stream().count());
        } catch (IOException e) {
            e.printStackTrace();

        }

        if(!files.isEmpty())
        {
            ForkJoinPool pool = new ForkJoinPool(10);
            long time = System.currentTimeMillis();
            try {
                List<Path> finalFiles = files;
                pool.submit(() -> {
                    finalFiles.stream().map(e -> {
                        try {
                            return Pair.of(e.getFileName().toString(), ImageIO.read(e.toFile()));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        return null;
                    }).map( e -> compute(e) ).forEach( e -> {
                        File outputFile = new File(args[1] + e.getLeft().toString());
                        try {
                            ImageIO.write(e.getRight(), "jpg", outputFile);
                        } catch (IOException ex) {
                            System.out.println("Write problem!!!");
                        }
                    });
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            pool.shutdown();
            System.out.println("Finished in: " + String.valueOf(System.currentTimeMillis() - time));
            System.out.println("Ended editing images!");
            //System.out.println("Starting edit!");
        }
    }
}
