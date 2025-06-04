package Utility;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class GraphicUtilies {
    public static boolean containsPoint(Point point, Point topleft, int width, int height) {
        return point.x > topleft.x && point.x < topleft.x + width && point.y > topleft.y && point.y < topleft.y + height;
    }

    public static boolean containsPoint(Point point, int x, int y, int width, int height) {
        return point.x > x && point.x < x + width && point.y > y && point.y < y + height;
    }

    public static BufferedImage rotateImage(BufferedImage image, float degree) {
        double angleInRadians = Math.toRadians(degree);
        AffineTransform transform = AffineTransform.getRotateInstance(angleInRadians, (double) image.getWidth() / 2, (double) image.getHeight() / 2);
        AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        transformOp.filter(image, rotatedImage);
        return rotatedImage;
    }
}
