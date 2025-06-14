/*
* TextTypewriterEngine creates a typewriter effect
*  */

package Components;

import Animation.Tween.AnimationTween;
import Animation.Tween.AnimationTweenBuilder;
import Animation.Tween.TweenListener;
import Core.GameSystem.JGameObjectInterface;
import Utility.Console;

import javax.swing.*;
import java.awt.*;

public class TextTypewriterEngine implements JGameObjectInterface, TweenListener {
    private String currentText;
    private AnimationTween tween;
    private JTextArea label;

    // Reference to the label, the text to be typewritten, and the duration for which it should last
    public void playTypewriterEffect(JTextArea label, String text, float duration) {
        currentText = text;

        tween = new AnimationTweenBuilder(AnimationTween.Tween.LINEAR, 0, text.length())
                .setDuration(duration)
                .setLoop(false)
                .setCallback(this)
                .build();

        this.label = label;
    }

    // Update loop
    // Updates the contents of the label according to the tween
    // which interpolates between the 0 and the length of the text
    public void update(float delta) {
        if (tween != null && tween.isPlaying()) {
            label.setText(currentText.substring(0, Math.round(tween.getLerpValue())));

            tween.update(delta);
        }
    }

    public void skipTextAnimation() {
        tween.complete();
    }

    public boolean isTextPlaying() {
        return tween.isPlaying();
    }

    @Override
    public void onUpdate(float value) {

    }

    @Override
    public void onIteration(int iteration) {

    }
}
