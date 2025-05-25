import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AnimationSprite implements IGameObject {
//    private ArrayList<Image>
    private float timeElapsed;
    private float prevFrameElapsed;
    private float AnimationInterval = 500;
    private int currentFrame;
    private final List<Image> Sprites = new ArrayList<>();

    public AnimationSprite(String... files) {
        reset();

        for (String file : files) {
            Image image = Toolkit.getDefaultToolkit().getImage(file);
            Sprites.add(image);
        }
    }

    public Image getCurrentSprite() {
        return Sprites.get(currentFrame);
    }

    public void reset() {
        prevFrameElapsed = 0.0f;
        timeElapsed = 0.0f;
        currentFrame = 0;
    }

    public void update(float delta) {
        timeElapsed += delta;
//        System.out.printf("%f, %f, %d\n", timeElapsed, prevFrameElapsed, currentFrame);
        if (timeElapsed - prevFrameElapsed > AnimationInterval) {
            prevFrameElapsed = timeElapsed;
            currentFrame = (currentFrame + 1) % Sprites.size();
        }
    }

    public void pause() {

    }
}
