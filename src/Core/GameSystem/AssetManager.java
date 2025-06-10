package Core.GameSystem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;

public class AssetManager {
    private static String resourceDirectory = "\\src\\Resources";
    private static String spriteResourceDirectory = "\\src\\Resources\\Sprites";
    private static String audioResourceDirectory = "\\src\\Resources\\Audio";
    private static String iconResourceDirectory = "\\src\\Resources\\Icons";
    private static HashMap<String, String> customDirectories = new HashMap<>();
    private static HashMap<String, Font> fonts = new HashMap<>();
//    private static HashMap<String, BufferedImage>

    public static void setCustomDirectory(String identifier, String path) {
        customDirectories.put(identifier, path);
    }

    public static String getCustomDirectoryPath(String directoryIdentifier, String filePath) {
        return System.getProperty("user.dir") + customDirectories.get(directoryIdentifier) + "\\" + filePath;
    }

    public static String getIconResourceDirectory(String filePath) {
        return System.getProperty("user.dir") + iconResourceDirectory + "\\" + filePath;
    }

    public static String getSpriteResourcePath(String filePath) {
        return System.getProperty("user.dir") + spriteResourceDirectory + "\\" + filePath;
    }

    public static BufferedImage getBufferedSprite(String filePath) {
        try {
            return ImageIO.read(new File(AssetManager.getSpriteResourcePath(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getBufferedSprite(String filePath, int width, int height) {
        return getBufferedSprite(filePath).getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public static Image getBufferedSpriteAbs(String absFilePath, int width, int height) {
        try {
            return ImageIO.read(new File(absFilePath)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Image getBufferedSpriteAspectRatioLocked(String filePath) {
        return getBufferedSprite(filePath).getScaledInstance(0, 0, Image.SCALE_SMOOTH);
    }


    public static Image getSprite(String filePath) {
        return Toolkit.getDefaultToolkit().getImage(AssetManager.getSpriteResourcePath(filePath));
    }

    public static String getAudioResourcePath(String filePath) {
        return System.getProperty("user.dir") + audioResourceDirectory + "\\" + filePath;
    }

//    TODO: method to return AudioStream

    public static String getResourceDirectory(String filePath) {
        return System.getProperty("user.dir") + resourceDirectory + "\\" + filePath;
    }

    public static String getRelativeDirectory(String filePath) {
        return System.getProperty("user.dir") + filePath;
    }

    public static void setSpriteResourceDirectory(String spriteResourceDirectory) {
        AssetManager.spriteResourceDirectory = spriteResourceDirectory;
    }

    public static String getSpriteResourceDirectory() {
        return spriteResourceDirectory;
    }

    public static String getResourceDirectory() {
        return resourceDirectory;
    }

    public static void setResourceDirectory(String resourceDirectory) {
        AssetManager.resourceDirectory = resourceDirectory;
    }

    public static HashMap<String, String> getCustomDirectories() {
        return customDirectories;
    }

    public static void setCustomDirectories(HashMap<String, String> customDirectories) {
        AssetManager.customDirectories = customDirectories;
    }

    public static String getAudioResourceDirectory() {
        return audioResourceDirectory;
    }

    public static void setAudioResourceDirectory(String audioResourceDirectory) {
        AssetManager.audioResourceDirectory = audioResourceDirectory;
    }

    public static void loadFont(String identifier, File fontFile) {
        try {
            Font newFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            fonts.put(identifier, newFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Font loadFontFromDirectory(String filePath) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(AssetManager.getResourceDirectory(filePath)));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Font getFont(String identifier) {
        return fonts.get(identifier);
    }

    public static void getAvailableFileName() {

    }

    public static String generateTimestampedFilename(String directory) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss");
        return String.format("%s\\%s.txt", directory, dateTimeFormatter.format(now));
    }
}
