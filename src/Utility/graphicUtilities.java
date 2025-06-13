package Utility;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class graphicUtilities {
//    private static final

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

    public static Graphics2D rotateGraphics(Graphics graphics, float degree) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        AffineTransform old = graphics2D.getTransform();
        old.rotate(Math.toRadians(degree));
        graphics2D.setTransform(old);
        return graphics2D;
    }

    public static Graphics2D rotateGraphics(Graphics graphics, int x, int y, float degree) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.rotate(Math.toRadians(degree), x, y);
        return graphics2D;
    }

    public static Graphics2D rotateYGraphics(Graphics graphics, int x, int y, float degree) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        double angle = Math.toRadians(degree);
        double scaleX = Math.cos(angle);
        AffineTransform affineTransform = graphics2D.getTransform();

        affineTransform.translate(x, y);
        affineTransform.scale(scaleX, 1.0);
        affineTransform.translate(-x, -y);

        graphics2D.setTransform(affineTransform);

        return graphics2D;
    }

    public static void applyGradient(Graphics graphics, Color color, int width, int height) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(width, 0,
                color.brighter().brighter(), 0, 0,
                color.darker().darker());

        graphics2D.setPaint(gp);
        graphics2D.fillRect(0, 0, width, height);
    }

    public static void applyLinearGraient(Graphics graphics, Color baseColor, int width, int height) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float[] fractions = {0.0f, 1.0f};
        Color transparent = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 0);
        Color[] colors = {
            baseColor.brighter().brighter(),
            transparent
        };

        LinearGradientPaint paint = new LinearGradientPaint(
            new Point2D.Float(0, 0),
            new Point2D.Float(width, 0),
            fractions,
            colors
        );

        g2d.setPaint(paint);
        g2d.fillRect(0, 0, width, height);
    }
}
