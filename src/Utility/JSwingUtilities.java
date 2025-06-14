/*
* JSwingUtilities is an extension of the SwingUtilities with convenient functions
*  */

package Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JSwingUtilities {
    /**
     * resizeFont resizes a component
     *
     * @param component the component to resize
     * @param size the new font size
     */
    public static void resizeFont(JComponent component, int size) {
        Font font = component.getFont(); // retrieve the font
        component.setFont(new Font(font.getFontName(), Font.PLAIN, size));
    }

    /**
     * resizeImageAspectLocked resizes a bufferedImage to a specified width whilst maintaining the aspect ratio
     *
     * @param bufferedImage the image to resize
     * @param width the width to resize to
     *
     * @return Image the resized image
     */
    public static Image resizeImageAspectLocked(BufferedImage bufferedImage, int width) {
        double aspectRatio = (double) bufferedImage.getHeight() / bufferedImage.getWidth();
        return bufferedImage.getScaledInstance(width, (int) (aspectRatio * width), Image.SCALE_SMOOTH);
    }

    /**
     * resizeImageAspectLocked resizes a bufferedImage to a specified width whilst maintaining the aspect ratio
     *
     * @param reference a component reference
     * @param bufferedImage the image to resize
     * @param width the width to resize to
     *
     * @return Image the resized image
     */
    public static Image resizeImageAspectLocked(JComponent reference, Image bufferedImage, int width) {
        double aspectRatio = (double) bufferedImage.getHeight(reference) / bufferedImage.getWidth(reference);
        return bufferedImage.getScaledInstance(width, (int) (aspectRatio * width), Image.SCALE_SMOOTH);
    }

    /**
     * resizeImageAspectLockedWithDimensions resizes an image with a locked aspect ratio to fit within
     * the dimensions of a rectangle
     *
     * @param bufferedImage the image to resize
     * @param width the maximum width
     * @param height the maximum hieght
     *
     * @return the resized image
     */
    public static Image resizeImageAspectLockedWithMinDimensions(BufferedImage bufferedImage, int width, int height) {
        double aspectRatio = (double) bufferedImage.getHeight() / bufferedImage.getWidth();

        if (width > height) {
            return bufferedImage.getScaledInstance(width, (int) (aspectRatio * width), Image.SCALE_SMOOTH);
        } else {
            return bufferedImage.getScaledInstance((int) (height / aspectRatio), height, Image.SCALE_SMOOTH);
        }
    }
}
