package Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SwingUtilities {
    public static void resizeFont(JComponent component, int size) {
        Font font = component.getFont();
        component.setFont(new Font(font.getFontName(), Font.PLAIN, size));
    }

    public static Image resizeImageAspectLocked(BufferedImage bufferedImage, int width) {
        double aspectRatio = (double) bufferedImage.getHeight() / bufferedImage.getWidth();
        return bufferedImage.getScaledInstance(width, (int) (aspectRatio * width), Image.SCALE_SMOOTH);
    }

    public static Image resizeImageAspectLockedWithMinDimensions(BufferedImage bufferedImage, int width, int height) {
        double aspectRatio = (double) bufferedImage.getHeight() / bufferedImage.getWidth();

        if (width > height) {
            return bufferedImage.getScaledInstance(width, (int) (aspectRatio * width), Image.SCALE_SMOOTH);
        } else {
            return bufferedImage.getScaledInstance((int) (height / aspectRatio), height, Image.SCALE_SMOOTH);
        }
    }
}
