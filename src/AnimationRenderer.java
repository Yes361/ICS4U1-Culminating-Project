import javax.swing.*;
import java.awt.*;

public class AnimationRenderer extends JComponent implements IGameObject {
    private final AnimationSprites animationSprites;
    private final JComponent componentReference;

    public AnimationRenderer(JComponent componentReference, AnimationSprites animationSprites) {
        this.componentReference = componentReference;
        this.animationSprites = animationSprites;
    }

    public void update(float delta) {
        animationSprites.update(delta);
    }

    public AnimationSprites getAnimationSprites() {
        return animationSprites;
    }

    public Graphics render(Graphics graphics) {
        Image currentFrame = animationSprites.getCurrentSprite();
        graphics.drawImage(currentFrame, 0, 0, componentReference);
        return graphics;
    }

    public Graphics render(Graphics graphics, int width, int height) {
        Image currentFrame = animationSprites.getCurrentSprite();
        graphics.drawImage(currentFrame, 0, 0, width, height, componentReference);
        return graphics;
    }
}