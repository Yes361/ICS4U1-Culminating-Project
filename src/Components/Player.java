package Components;

import Animation.*;
import Animation.Tween.AnimationTween;
import Animation.Tween.AnimationTweenBuilder;
import Core.Input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
public class Player extends JPanel {

    private final Input input = new Input();

    AnimationRenderer animationRenderer;
    AnimationTween tween;
    TypewriterLabel label;

    public Player() {
        setBounds(0, 0, 50, 50);

        tween = new AnimationTweenBuilder(AnimationTween.Tween.LINEAR, 0, 480)
//            .setCallback(value -> this.setLocation((int) value, this.getY()))
            .setDirection(AnimationTween.AnimationProperties.REVERSED_DIRECTION)
            .build();

        addKeyListener(input);

        AnimationSprite idle = new AnimationSprite(
            500,
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Untitled design.jpg",
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Untitled design (1).jpg",
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Untitled design (2).jpg"
        );

        AnimationSprite something_else = new AnimationSprite(
            500,
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Seraphina-1.png.png"
        );

        AnimationSprites sprites = new AnimationSprites();
        sprites.addAnimation("idle", idle);
        sprites.addAnimation("walk", something_else);

        animationRenderer = new AnimationRenderer(this, sprites);
        animationRenderer.setCurrentAnimation("walk");
    }


    public void update(float delta) {
        int x = getX();
        int y = getY();
        float speed = 1;

        if (input.isKeyPressed(KeyEvent.VK_UP)) {
            y -= (int) (speed * delta);
        }

        if (input.isKeyPressed(KeyEvent.VK_DOWN)) {
            y += (int) (speed * delta);
        }

        if (input.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= (int) (speed * delta);
        }

        if (input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += (int) (speed * delta);
        }

        setLocation(x, y);

        animationRenderer.update(delta);
        tween.update(delta);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.scale(3.0, 3.0);
        animationRenderer.render(graphics2D, 50, 50);
    }
}
