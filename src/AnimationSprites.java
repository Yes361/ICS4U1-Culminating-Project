import jdk.jfr.Event;

import java.awt.*;
import java.util.HashMap;

public class AnimationSprites implements IGameObject {
    private final HashMap<String, AnimationSprite> animations = new HashMap<>();
    private String currentAnimation;
    private final EventEmitter eventEmitter = new EventEmitter();

    public AnimationSprites() {}

    public void addAnimation(String identifier, AnimationSprite animation) {
        if (animations.isEmpty()) {
            currentAnimation = identifier;
        }

        animations.put(identifier, animation);
    }

    public void update(float delta) {
        if (!animations.isEmpty()) {
            getCurrentAnimationSprite().update(delta, new AnimationEvent() {
                @Override
                public void onEvent(Object... args) {
                    eventEmitter.emit("onAnimation", currentAnimation, animations.get(currentAnimation).getCurrentFrameIndex());
                }
            });
        }
    }

    public void onAnimationEvent(AnimationEvent event) {
        eventEmitter.subscribe("onAnimation", event);
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
