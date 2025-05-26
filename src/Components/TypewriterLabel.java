package Components;

import javax.swing.*;

public class TypewriterLabel extends JLabel {
    private String TypewriterWord;
    private int currentIndex = 0;
    private float duration = 0;
    private float timeElapsed = 0;
    private float prevLetterElapsed = 0;
//    private

    public TypewriterLabel(String TypewriterWord) {
        applyEffect(TypewriterWord, 1000);
    }

    public TypewriterLabel(String TypewriterWord, float duration) {
        applyEffect(TypewriterWord, duration);
    }

    public void applyEffect(String TypewriterWord, float duration) {
        this.TypewriterWord = TypewriterWord;
        this.duration = duration;

        currentIndex = 0;
        timeElapsed = 0;
        prevLetterElapsed = 0;
    }

    public void update(float delta) {
        if (currentIndex == TypewriterWord.length()) {
            return;
        }

        float increment = duration / TypewriterWord.length();
        if (timeElapsed - prevLetterElapsed > increment) {
            prevLetterElapsed = timeElapsed;
            currentIndex++;

            setText(TypewriterWord.substring(0, currentIndex));
        }
        timeElapsed += delta;
    }
}
