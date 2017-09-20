package com.bogdan.comparator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ImagesDifferencesFinder {

    private Set<Rectangle> rectanglesForExclucion = new HashSet<>();

    public ImagesDifferencesFinder() {

    }

    public ImagesDifferencesFinder(Set<Rectangle> rectanglesForExclucion) {
        this.rectanglesForExclucion = rectanglesForExclucion;
    }

    public BufferedImage getImageWithDiffs(BufferedImage image1, BufferedImage image2) {

        validateSizesOfImages(image1, image2);

        DifferentPixelsFinder differentPixelsFinder = new DifferentPixelsFinder(rectanglesForExclucion);
        Set<Point> differentPixels = differentPixelsFinder.getDifferentPixelsAsPoints(image1, image2);

        if (differentPixels.isEmpty()) {
            return image1;
        }

        Set<Set<Point>> clusters = new ClustersFinder().findPixelsClusters(differentPixels);

        Set<Rectangle> rectangles = calculateRectangles(clusters);

        drawRectangles(image2, rectangles);

        return image2;
    }

    private Set<Rectangle> calculateRectangles(Set<Set<Point>> clusters) {
        return clusters.stream()
                .map(new PixelsClusterToBindingRectangleMapper())
                .collect(Collectors.toSet());
    }

    private void drawRectangles(BufferedImage image, Set<Rectangle> rectangles) {

        Graphics graphics = image.createGraphics();

        rectangles.forEach(r ->
        {
            graphics. setColor(Color.RED);
            graphics.draw3DRect((int) r.getX(),
                    (int) r.getY(),
                    (int) r.getWidth(),
                    (int) r.getHeight(), false);
        });
    }

    private void validateSizesOfImages(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            throw new IllegalArgumentException("Images must have the similar sizes!");
        }
    }
}