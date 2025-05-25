import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnimationSprites implements IGameObject {
//    private ArrayList<Image>
    private float timeElapsed;
    private float prevFrameElapsed;
    private float AnimationInterval = 500;
    private int currentFrame;
    private final List<Image> Sprites = new ArrayList<>();

    public AnimationSprites(Component component, String... files) {
        MediaTracker track = new MediaTracker(component);

        for (int i = 0; i < files.length; i++) {
            Image image = Toolkit.getDefaultToolkit().getImage(files[i]);
            Sprites.add(image);
            track.addImage(image, i);
        }

        try {
            track.waitForAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        reset();
    }

    public void reset() {
        prevFrameElapsed = 0.0f;
        timeElapsed = 0.0f;
        currentFrame = 0;
    }

    public void update(float delta) {
        timeElapsed += delta;
        if (timeElapsed - prevFrameElapsed > AnimationInterval) {
            prevFrameElapsed = timeElapsed;
            currentFrame = (currentFrame + 1) % Sprites.size();
        }
    }

    public void pause() {

    }
}
