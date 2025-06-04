package Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimationSprite {
    private final List<FrameSprite> Sprites = new ArrayList<>();

    public AnimationSprite(float AnimationInterval, String... files) {
        for (String file : files) {
            addSprite(file, AnimationInterval);
        }
    }

    public AnimationSprite(List<String> filePaths, float AnimationInterval) {
        for (String file : filePaths) {
            addSprite(file, AnimationInterval);
        }
    }

    public void addSprite(String filePath, float duration) {
        try {
            Sprites.add(new FrameSprite(ImageIO.read(new File(filePath)), duration));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FrameSprite getSprite(int index) {
        return Sprites.get(index);
    }

    public int getSpriteCount() {
        return Sprites.size();
    }
}
