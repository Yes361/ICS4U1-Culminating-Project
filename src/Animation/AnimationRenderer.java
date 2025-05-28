package Animation;

import Utility.EventEmitter;

import javax.swing.*;
import java.awt.*;

public class AnimationRenderer {
    private final AnimationSprites animationSprites;
    private String currentAnimationName;
    private AnimationSprite currentAnimation;
    private final JComponent componentReference;
    private float timeElapsed;
    private float prevFrameElapsed;
    private int currentFrame;
    private final EventEmitter eventEmitter = new EventEmitter();


    public AnimationRenderer(JComponent componentReference, AnimationSprites animationSprites) {
        ResetAnimation();

        this.componentReference = componentReference;
        this.animationSprites = animationSprites;
    }

    public void ResetAnimation() {
        timeElapsed = 0;
        prevFrameElapsed = 0;
        currentFrame = 0;
    }

    public void update(float delta) {
        if (currentAnimation == null) {
            return;
        }

        timeElapsed += delta;

        if (timeElapsed - prevFrameElapsed > getCurrentSprite().duration()) {
            prevFrameElapsed = timeElapsed;
            currentFrame = (currentFrame + 1) % currentAnimation.getSpriteCount();

            componentReference.repaint();
            eventEmitter.emit("onAnimationChange", currentAnimationName, currentFrame);
        }
    }

    public void onAnimationEvent(AnimationEvent event) {
        eventEmitter.subscribe("onAnimationChange", event);
    }

    public void setCurrentAnimation(String AnimationName) {
        currentAnimation = animationSprites.getAnimationSprite(AnimationName);
    }

    public String getCurrentAnimationName() {
        return currentAnimationName;
    }

    public AnimationSprites getAnimationSprites() {
        return animationSprites;
    }

    public FrameSprite getCurrentSprite() {
        return currentAnimation.getSprite(currentFrame);
    }

    public Graphics render(Graphics graphics) {
        if (currentAnimation != null) {
            Image currentImage = getCurrentSprite().image();
            graphics.drawImage(currentImage, 0, 0, componentReference);
        }

        return graphics;
    }

    public Graphics render(Graphics graphics, int width, int height) {
        if (currentAnimation != null) {
            Image currentFrame = getCurrentSprite().image();
            graphics.drawImage(currentFrame, 0, 0, width, height, componentReference);
        }

        return graphics;
    }
}