package Components;

import Animation.*;
import Core.Input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends JComponent {

    private final Input input = new Input();

    AnimationRenderer animationRenderer;
    TypewriterLabel label;

    float x;
    float y;

    public Player() {
        addKeyListener(input);
        setSize(100, 100);

        AnimationSprite walkAnimation = new AnimationSprite(
            100,
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Seraphina-1.png.png",
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Seraphina-2.png.png",
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Seraphina-3.png.png",
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Seraphina-4.png.png"
        );

        AnimationSprites sprites = new AnimationSprites();
        sprites.addAnimation("walk", walkAnimation);

        animationRenderer = new AnimationRenderer(this, sprites);
        animationRenderer.setCurrentAnimation("walk");
    }


    public void update(float delta) {
        float speed = 0.5f;

        if (input.isKeyPressed(KeyEvent.VK_UP)) {
            y -= speed * delta;
        }

        if (input.isKeyPressed(KeyEvent.VK_DOWN)) {
            y += speed * delta;
        }

        if (input.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= speed * delta;
        }

        if (input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += speed * delta;
        }

        setLocation((int) x, (int) y);

        animationRenderer.update(delta);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        animationRenderer.render(graphics2D, getWidth(), getHeight());
    }
}
