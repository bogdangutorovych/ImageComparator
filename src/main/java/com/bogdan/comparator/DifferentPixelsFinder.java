package com.bogdan.comparator;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class DifferentPixelsFinder {

    private static final float RGB_MAX_VALUE = 255;

    private static final int PIXELS_DIFFERENCE_PERCENTAGE = 10;

    private Set<Rectangle> rectanglesForExclucion;

    public DifferentPixelsFinder(Set<Rectangle> rectanglesToExclude) {
        this.rectanglesForExclucion = rectanglesToExclude;
    }

    public Set<Point> getDifferentPixelsAsPoints(BufferedImage image1, BufferedImage image2) {

        Set<Point> differentPixels = new HashSet<>();

        for (int i = 0; i < image1.getWidth(); i++) {
            for (int j = 0; j < image1.getHeight(); j++) {

                Point pixel = new Point(i, j);

                if (isInExcludedArea(pixel)) {
                    continue;
                }

                Color rgbColor1 = new Color(image1.getRGB(i, j));
                Color rgbColor2 = new Color(image2.getRGB(i, j));
                
                if (arePixelsDifferent(rgbColor1, rgbColor2)) {
                    differentPixels.add(new Point(i, j));
                }

            }
        }

        return differentPixels;
    }

    private boolean isInExcludedArea(Point pixel) {
        return rectanglesForExclucion.stream().filter(r -> r.contains(pixel)).count() > 0;
    }

    private boolean arePixelsDifferent(Color rgbColor1, Color rgbColor2) {

        if (rgbColor1 == rgbColor2) {
            return false;
        }
        
        int red1 = rgbColor1.getRed();
        int green1 = rgbColor1.getGreen();
        int blue1 = rgbColor1.getBlue();

        int red2 = rgbColor2.getRed();
        int green2 = rgbColor2.getGreen();
        int blue2 = rgbColor2.getBlue();
        
        float redDiffRatio = (float) Math.abs(red1 - red2) / RGB_MAX_VALUE;
        float greenDiffRatio = (float) Math.abs(green1 - green2) / RGB_MAX_VALUE;
        float blueDiffRatio = (float) Math.abs(blue1 - blue2) / RGB_MAX_VALUE;

        float pixelDiffRatio = (redDiffRatio + greenDiffRatio + blueDiffRatio) / 3;

        return pixelDiffRatio > PIXELS_DIFFERENCE_PERCENTAGE / 100;
    }
}
