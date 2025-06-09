package Components;

import Animation.Tween.AnimationTween;
import Animation.Tween.AnimationTweenBuilder;
import Core.GameSystem.JGameObject;
import Utility.Console;
import Utility.EventEmitter;
import Utility.EventListener;

import java.awt.*;
import java.awt.geom.Point2D;

public class Camera2D extends JGameObject {
    private float x = 0;
    private float y = 0;
    private AnimationTween fadeTween;
    private String currentEffect;
    private EventEmitter eventEmitter = new EventEmitter();

    public Camera2D() {
        this(0, 0);
    }

    public Camera2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void startFade() {
        if (fadeTween == null || !fadeTween.isPlaying()) {
            fadeTween = new AnimationTweenBuilder(AnimationTween.Tween.LINEAR, 0, 1).setDuration(500).build();
            currentEffect = "Fade";
        }
    }

    public void startUnFade() {
        if (fadeTween == null || !fadeTween.isPlaying()) {
            fadeTween = new AnimationTweenBuilder(AnimationTween.Tween.LINEAR, 1, 0).setDuration(500).build();
            currentEffect = "Unfade";
        }
    }

    public void onEffectFinish(EventListener eventListener) {
        eventEmitter.subscribe("onEffectFinish", eventListener);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (fadeTween != null) {
            fadeTween.update(delta);

            if (!fadeTween.isPlaying()) {
                eventEmitter.emit("onEffectFinish", currentEffect);
                fadeTween = null;
            }
        }

        repaint();
    }

    public void setCenter(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point2D.Float getCenter() {
        return new Point2D.Float(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (fadeTween != null) {
            Graphics2D graphics2D = (Graphics2D) g;

            float opacity = fadeTween.getLerpValue();
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        }

        super.paintComponent(g);

        g.translate((int) x, (int) y);
    }
}
