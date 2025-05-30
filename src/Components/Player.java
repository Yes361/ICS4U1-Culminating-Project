package Components;

import Animation.*;
import Core.GameSystem.AssetManager;
import Core.GameSystem.AudioManager;
import Core.GameSystem.JGameObject;
import Core.Input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends JGameObject {

    private final Input input = new Input() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT:
//                    AudioManager.play(AssetManager.getAudioResourcePath("Dress Shoe Walking Down Stairs Sound Effect.wav"));
                break;
                default:
                break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);

//            AudioManager.stop();
        }
    };

    AnimationRenderer animationRenderer;

    float x;
    float y;

    public Player() {
        addKeyListener(input);
        setSize(100, 100);
        setLocation(0, 0);

        x = getX();
        y = getY();

        AnimationSprite UpAnimation = new AnimationSprite(
            100,
            AssetManager.getSpriteResourcePath("Jasper\\Jasper-1.png.png"),
            AssetManager.getSpriteResourcePath("Jasper\\Jasper-2.png.png"),
            AssetManager.getSpriteResourcePath("Jasper\\Jasper-3.png.png"),
            AssetManager.getSpriteResourcePath("Jasper\\Jasper-4.png.png")
        );

        AnimationSprite LeftAnimation = new AnimationSprite(
                50,
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide-1.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide-2.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide-3.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide-4.png.png")
        );

        AnimationSprite RightAnimation = new AnimationSprite(
                50,
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide2-1.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide2-2.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide2-3.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide2-4.png.png")
        );

        AnimationSprite DownAnimation = new AnimationSprite(
                100,
                AssetManager.getSpriteResourcePath("Jasper\\JasperBack-1.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperBack-2.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperBack-3.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperBack-4.png.png")
        );

        AnimationSprite IdleAnimation = new AnimationSprite(
                100,
                AssetManager.getSpriteResourcePath("Jasper\\Jasper-1.png.png")
        );

        AnimationSprites sprites = new AnimationSprites();
        sprites.addAnimation("down", DownAnimation);
        sprites.addAnimation("left", LeftAnimation);
        sprites.addAnimation("right", RightAnimation);
        sprites.addAnimation("up", UpAnimation);
        sprites.addAnimation("idle", IdleAnimation);

        animationRenderer = new AnimationRenderer(this, sprites);
        animationRenderer.setCurrentAnimation("idle");

        repaint();
    }


    public void update(float delta) {
        float speed = 0.5f;

        if (input.isKeyPressed(KeyEvent.VK_UP)) {
            y -= speed * delta;
            animationRenderer.setCurrentAnimation("down");
        } else if (input.isKeyPressed(KeyEvent.VK_DOWN)) {
            y += speed * delta;
            animationRenderer.setCurrentAnimation("up");
        } else if (input.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= speed * delta;
            animationRenderer.setCurrentAnimation("left");
        } else if (input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += speed * delta;
            animationRenderer.setCurrentAnimation("right");
        } else {
            animationRenderer.setCurrentAnimation("idle");
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
