package Animation;

import java.awt.*;
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
        Image image = Toolkit.getDefaultToolkit().getImage(filePath);
        Sprites.add(new FrameSprite(image, duration));
    }

    public FrameSprite getSprite(int index) {
        return Sprites.get(index);
    }

    public int getSpriteCount() {
        return Sprites.size();
    }
}
