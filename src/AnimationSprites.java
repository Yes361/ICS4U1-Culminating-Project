import java.awt.*;
import java.util.HashMap;

public class AnimationSprites implements IGameObject {
    final private HashMap<String, AnimationSprite> animations = new HashMap<>();
    private String currentAnimation;

    public AnimationSprites() {}

    public void addAnimation(String identifier, AnimationSprite animation) {
        if (animations.isEmpty()) {
            currentAnimation = identifier;
        }

        animations.put(identifier, animation);
    }

    public void update(float delta) {
        if (!animations.isEmpty()) {
            getCurrentAnimationSprite().update(delta);
        }
    }

    public String getCurrentAnimation() {
        return currentAnimation;
    }

    public Image getCurrentSprite() {
        return animations.get(currentAnimation).getCurrentSprite();
    }

    public void setCurrentAnimation(String currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public AnimationSprite getCurrentAnimationSprite() {
        return animations.get(currentAnimation);
    }
}
