package Animation.Tween;

import Utility.Console;
import Utility.MathUtilities;

public class AnimationTween {
    private Tween type;
    private float start;
    private float end;
    private float value;
    private boolean loop;
    private int animationIteration;
    private int currentIteration = 0;
    private AnimationProperties animationDirection;
    private float animationDuration;
    private boolean currentDirection;
    private float timeElapsed = 0;
    private float animationElapsed = 0;
    private boolean isPlaying = true;
    private TweenListener callback;

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

    public void resetTween() {
        if (animationDirection == AnimationProperties.NORMAL_DIRECTION) {
            this.currentDirection = true;
        } else if (animationDirection == AnimationProperties.REVERSED_DIRECTION) {
            this.currentDirection = false;
        }

        timeElapsed = 0;
        animationElapsed = 0;
        currentIteration = 0;
        isPlaying = true;
    }

    public void update(float delta) {
        if (currentIteration > 0 && !loop) {
            return;
        }
//
//        if (currentIteration > animationIteration) {
//            return;
//        }

        switch (type) {
            case LINEAR:
                value = animationElapsed / animationDuration;
                break;
            case CUBIC:
                break;
            case DISCRETE:
                break;
        }

        if (currentDirection) {
            animationElapsed += delta;
        } else {
            animationElapsed -= delta;
        }

        timeElapsed += delta;
        isPlaying = !(value > 1 || value < 0);

        if (value > 1 || value < 0) {
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

            currentIteration++;

            if (callback != null) {
                callback.onIteration(currentIteration);
            }
        }

        if (callback != null) {
            callback.onUpdate(getLerpValue());
        }
    }

    public void complete() {
//        value = 1;
        animationElapsed = animationDuration;
        isPlaying = true;
    }

    // TODO: Possible implementation with Java Swing timer?
    public void updateAsync() {

    }

    public float getValue() {
        return value;
    }

    public float getLerpValue() {
        return (end - start) * value + start;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
