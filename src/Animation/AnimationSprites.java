package Animation;

import java.util.HashMap;

public class AnimationSprites {
    private final HashMap<String, AnimationSprite> animations = new HashMap<>();

    public void addAnimation(String identifier, AnimationSprite animation) {
        animations.put(identifier, animation);
    }

    public AnimationSprite getAnimationSprite(String identifier) {
        return animations.get(identifier);
    }

    public HashMap<String, AnimationSprite> getAnimations() {
        return animations;
    }
}
