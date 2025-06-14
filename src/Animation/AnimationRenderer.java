/*  */

package Animation;

import Utility.Console;
import Utility.EventEmitter;

import javax.swing.*;
import java.awt.*;

public class AnimationRenderer {
    // References
    private final AnimationSprites animationSprites;
    private AnimationSprite currentAnimation;
    private final JComponent componentReference;

    // State variables
    private String currentAnimationName = "";
    private float timeElapsed;
    private float prevFrameElapsed;
    private int currentFrame;
    private boolean paused = false;

    // Handles animation-related event handling
    private final EventEmitter eventEmitter = new EventEmitter();

    public AnimationRenderer(JComponent componentReference, AnimationSprites animationSprites) {
        ResetAnimation();

        this.componentReference = componentReference;
        this.animationSprites = animationSprites;
    }

    // Resetting the animation
    public void ResetAnimation() {
        timeElapsed = 0;
        prevFrameElapsed = 0;
        currentFrame = 0;
    }

    public void update(float delta) {
        if (currentAnimation == null) {
            return;
        }

        // If the renderer is paused, do not increment timeElapsed
        if (!paused) {
            timeElapsed += delta;
        }

        // If the difference in time between now and
        // when the last frame exceeds the duration for the current sprite
        // increment to the next frame
        if (timeElapsed - prevFrameElapsed > getCurrentSprite().duration()) {
            prevFrameElapsed = timeElapsed;

            // modular operation to ensure the animation loops
            currentFrame = (currentFrame + 1) % currentAnimation.getSpriteCount();
            componentReference.repaint();

            // Emit the change for fellow classes subscribed to this event to learn about!
            eventEmitter.emit("onAnimationChange", currentAnimationName, currentFrame);
        }
    }

    public void skipToFrame(int index) {
        currentFrame = index;
    }

    public void skipToLastFrame() {
        currentFrame = currentAnimation.getSpriteCount() - 1;
    }

    public void skipToFirstFrame() {
        currentFrame = 0;
    }

    public int getFrameCount() {
        return currentAnimation.getSpriteCount();
    }

    public void togglePaused() {
        paused = !paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    // Objects can subscribe to animation events through this method
    public void onAnimationEvent(AnimationEvent event) {
        eventEmitter.subscribe("onAnimationChange", event);
    }

    // Sets the current animation as indicated by the animation name
    public void setCurrentAnimation(String AnimationName) {
        paused = false;

        if (!currentAnimationName.equals(AnimationName)) {
            currentAnimationName = AnimationName;
            currentAnimation = animationSprites.getAnimationSprite(AnimationName);

            ResetAnimation();
        }
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
//        graphics.drawImage(, 0, 0, width, height, componentReference);
        if (currentAnimation != null) {
            Image currentFrame = getCurrentSprite().image().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//            graphics.drawRect(0, 0, width, height);
            graphics.drawImage(currentFrame, 0, 0, width, height, componentReference);
        }

        return graphics;
    }
}