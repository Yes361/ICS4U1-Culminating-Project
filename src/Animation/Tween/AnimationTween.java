package Animation.Tween;

public class AnimationTween {
    private Tween type;
    private float start;
    private float end;
    private float value;
    private boolean loop;
    private int animationIteration;
    private AnimationProperties animationDirection;
    private float animationDuration;
    private boolean currentDirection;
    private float timeElapsed = 0;
    private float animationElapsed = 0;
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
    }

    public void update(float delta) {
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
        }

        if (callback != null) {
            callback.onUpdate(getLerpValue());
        }
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
}
