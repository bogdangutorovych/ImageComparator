package com.bogdan.comparator;

import java.awt.image.BufferedImage;

public class Runner {

    private static final String IMAGE_TYPE = "png";

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Only 3 arguments should be specified: pathToImage1, pathToImage2, pathToImageWithDiffs");
            System.exit(1);
        }

        String pathToImage1 = args[0];
        String pathToImage2 = args[1];
        String pathToImageWithDiffs = args[2];

        ImageReader imageReader = new ImageReader();
        BufferedImage image1 = imageReader.readImageFromFile(pathToImage1);
        BufferedImage image2 = imageReader.readImageFromFile(pathToImage2);

        BufferedImage diff = new ImagesDifferencesFinder().getImageWithDiffs(image1, image2);

        new ImageWriter().writeImageToFile(diff, IMAGE_TYPE, pathToImageWithDiffs);
    }
}
