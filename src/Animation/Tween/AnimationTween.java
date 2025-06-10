package Animation.Tween;

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
        if (!isPlaying) return;

        // Update progress
        switch (type) {
            case LINEAR:
                value = animationElapsed / animationDuration;
                break;
            case CUBIC:
                // TODO
                break;
            case DISCRETE:
                // TODO
                break;
        }

        // Advance time
        animationElapsed += currentDirection ? delta : -delta;
        timeElapsed += delta;

        // Check bounds and handle iteration/loop logic
        boolean outOfBounds = (currentDirection && animationElapsed >= animationDuration) ||
                (!currentDirection && animationElapsed <= 0);

        if (outOfBounds) {
            currentIteration++;

            if (!loop && animationIteration > 0 && currentIteration >= animationIteration) {
                isPlaying = false;
                animationElapsed = currentDirection ? animationDuration : 0;
                value = currentDirection ? 1 : 0;
                if (callback != null) callback.onUpdate(getLerpValue());
                if (callback != null) callback.onIteration(currentIteration);
                return;
            }

            if (animationDirection == AnimationProperties.REVERSED_DIRECTION) {
                currentDirection = !currentDirection;
            }

            animationElapsed = currentDirection ? 0 : animationDuration;
            value = currentDirection ? 0 : 1;

            if (callback != null) callback.onIteration(currentIteration);
        }

        // Clamp and update
        animationElapsed = Math.max(0, Math.min(animationElapsed, animationDuration));
        value = animationElapsed / animationDuration;

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
