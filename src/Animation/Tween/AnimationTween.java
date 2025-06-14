/*
* AnimationTween handles the heavy duty of
* animating between two values
*  */

package Animation.Tween;

import Utility.Console;
import Utility.MathUtilities;

public class AnimationTween {
    // current state variables
    private Tween type;
    private float start;
    private float end;
    private float value;
    private boolean loop;

    private boolean currentDirection;
    private float timeElapsed = 0;
    private boolean isPlaying = true;

    // Animation properties for this tween
    private int animationIteration;
    private int currentIteration = 0;
    private AnimationProperties animationDirection;
    private float animationDuration;
    private float animationElapsed = 0;

    private TweenListener callback;

    // Types of Tweening
    public enum Tween {
        LINEAR,
        CUBIC,
        DISCRETE
    }

    public enum AnimationProperties {
        REVERSED_DIRECTION,
        NORMAL_DIRECTION,
    }

    public AnimationTween(Tween type) {
        this(type, true, 0, AnimationProperties.NORMAL_DIRECTION, 1000, null, 0, 0);
    }

    public AnimationTween(Tween type, boolean loop, int animationIteration, AnimationProperties animationDirection, float animationDuration, TweenListener callback, float start, float end) {
        this.type = type;
        this.loop = loop;
        this.animationIteration = animationIteration;
        this.animationDirection = animationDirection;
        this.animationDuration = animationDuration;
        this.callback = callback;
        this.start = start;
        this.end = end;

        resetTween();
    }

//    TODO: Copying base values from Tween to construct new Tween
//    public static Animation.Tween.AnimationTweenBuilder newBuilder() {
//        return new Animation.Tween.AnimationTweenBuilder();
//    }

    /**
     * resetTween resets the tween !
     */
    public void resetTween() {
        // setting the current direction based on the animation property
        if (animationDirection == AnimationProperties.NORMAL_DIRECTION) {
            this.currentDirection = true;
        } else if (animationDirection == AnimationProperties.REVERSED_DIRECTION) {
            this.currentDirection = false;
        }

        // resetting state variables
        timeElapsed = 0;
        animationElapsed = 0;
        currentIteration = 0;
        isPlaying = true;
    }

    public void update(float delta) {
        // If the tween isn't meant to loop, and its current number
        // of iterations exceeds a non-zero amount, we stop
        if (currentIteration > 0 && !loop) {
            return;
        }

        // TODO: Implementation of iteration counting
//        if (currentIteration > animationIteration) {
//            return;
//        }

        // TODO: Implementing tweening for different types
        switch (type) {
            case LINEAR:
                // Linear tween is easiest as it is just a value from 0 -> 1 that
                // directly corresponds to the fractional value of the
                // current value divided by the total supposed value
                value = animationElapsed / animationDuration;
                break;
            case CUBIC:
                break;
            case DISCRETE:
                break;
        }

        // based on the current direction adjust the
        // animationElapsed accordingly
        if (currentDirection) {
            animationElapsed += delta;
        } else {
            animationElapsed -= delta;
        }

        timeElapsed += delta;
        isPlaying = !(value > 1 || value < 0);

        // If the value exceeds the [0, 1] bounds, determine the preceeding behavior
        if (value > 1 || value < 0) {
            // if the animation properties state the tween
            // reverses directions in successive iterations
            // that is done accordingly
            if (animationDirection == AnimationProperties.REVERSED_DIRECTION) {
                currentDirection = !currentDirection;
                if (currentDirection) {
                    animationElapsed = 0;
                } else {
                    animationElapsed = animationDuration;
                }
            } else {
                animationElapsed = 0;
            }

            value = MathUtilities.constrain(0, 1, value);

            currentIteration++; // increment current iteration

            // if the callback is not null, call it
            if (callback != null) {
                callback.onIteration(currentIteration);
            }
        }

        if (callback != null) {
            callback.onUpdate(getLerpValue());
        }
    }

    // Completes the tween and quits early
    public void complete() {
        animationElapsed = animationDuration;
        isPlaying = false;
    }

    // TODO: Possible implementation with Java Swing timer?
    public void updateAsync() {}

    // Get the current value of the tween from 0 -> 1
    public float getValue() {
        return value;
    }

    // Get the value of the tween mapped to the range [start, end]
    public float getLerpValue() {
        return (end - start) * value + start;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
