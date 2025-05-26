package Animation.Tween;

public class AnimationTweenBuilder {
    private AnimationTween.Tween type;
    private boolean loop = true;
    private int animationIteration = 0;
    private AnimationTween.AnimationProperties animationDirection = AnimationTween.AnimationProperties.NORMAL_DIRECTION;
    private float animationDuration = 1000;
    private TweenListener callback = null;
    private float start;
    private float end;

    public AnimationTweenBuilder(AnimationTween.Tween type, float start, float end) {
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public AnimationTweenBuilder setCallback(TweenListener callback) {
        this.callback = callback;
        return this;
    }

    public AnimationTweenBuilder setDirection(AnimationTween.AnimationProperties animationDirection) {
        this.animationDirection = animationDirection;
        return this;
    }

    public AnimationTween build() {
        return new AnimationTween(type, loop, animationIteration, animationDirection, animationDuration, callback, start, end);
    }
}
