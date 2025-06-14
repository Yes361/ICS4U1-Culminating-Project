package Components;

import Animation.*;
import Core.GameSystem.AssetManager;
import Core.GameSystem.JGameObject;
import Utility.EventEmitter;
import Utility.EventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

public class Player extends JGameObject implements CollisionListener {
    private EventEmitter eventEmitter = new EventEmitter();
    private AnimationRenderer animationRenderer;

    private float worldX;
    private float worldY;
    private final float speed = 0.3f;

    private final Set<Integer> keysPressed = new HashSet<>();

    public Player() {
        setFocusable(true);
        setSize(100, 100);
        setLocation(0, 0);
        worldX = getX();
        worldY = getY();

        setupAnimations();
        setupKeyBindings();
    }

    private void setupKeyBindings() {
        int condition = WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();

        bindKey(inputMap, actionMap, "UP", KeyEvent.VK_UP, true);
        bindKey(inputMap, actionMap, "UP_RELEASED", KeyEvent.VK_UP, false);
        bindKey(inputMap, actionMap, "DOWN", KeyEvent.VK_DOWN, true);
        bindKey(inputMap, actionMap, "DOWN_RELEASED", KeyEvent.VK_DOWN, false);
        bindKey(inputMap, actionMap, "LEFT", KeyEvent.VK_LEFT, true);
        bindKey(inputMap, actionMap, "LEFT_RELEASED", KeyEvent.VK_LEFT, false);
        bindKey(inputMap, actionMap, "RIGHT", KeyEvent.VK_RIGHT, true);
        bindKey(inputMap, actionMap, "RIGHT_RELEASED", KeyEvent.VK_RIGHT, false);
    }

    private void bindKey(InputMap inputMap, ActionMap actionMap, String name, int keyCode, boolean pressed) {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, 0, !pressed);
        inputMap.put(keyStroke, name);

        actionMap.put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pressed) {
                    keysPressed.add(keyCode);
                    // Uncomment if you want a walking sound effect
                    // AudioManager.play(...);
                } else {
                    keysPressed.remove(keyCode);
                }
            }
        });
    }

    private void setupAnimations() {
        AnimationSprite UpAnimation = new AnimationSprite(100,
                AssetManager.getSpriteResourcePath("Jasper\\Jasper-1.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\Jasper-2.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\Jasper-3.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\Jasper-4.png.png")
        );

        AnimationSprite LeftAnimation = new AnimationSprite(100,
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide-1.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide-2.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide-3.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide-4.png.png")
        );

        AnimationSprite RightAnimation = new AnimationSprite(100,
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide2-1.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide2-2.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide2-3.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperSide2-4.png.png")
        );

        AnimationSprite DownAnimation = new AnimationSprite(100,
                AssetManager.getSpriteResourcePath("Jasper\\JasperBack-1.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperBack-2.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperBack-3.png.png"),
                AssetManager.getSpriteResourcePath("Jasper\\JasperBack-4.png.png")
        );

        AnimationSprite IdleAnimation = new AnimationSprite(100,
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
    }

    public void onMove(EventListener eventListener) {
        eventEmitter.subscribe("onMove", eventListener);
    }

    public void update(float delta) {
        boolean moved = false;

        float deltaY = 0, deltaX = 0;

        if (keysPressed.contains(KeyEvent.VK_UP)) {
            deltaY -= speed * delta;
            animationRenderer.setCurrentAnimation("down");
            moved = true;
        } else if (keysPressed.contains(KeyEvent.VK_DOWN)) {
            deltaY += speed * delta;
            animationRenderer.setCurrentAnimation("up");
            moved = true;
        } else if (keysPressed.contains(KeyEvent.VK_LEFT)) {
            deltaX -= speed * delta;
            animationRenderer.setCurrentAnimation("left");
            moved = true;
        } else if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
            deltaX += speed * delta;
            animationRenderer.setCurrentAnimation("right");
            moved = true;
        }

        if (moved) {
            eventEmitter.emit("onMove", worldX, worldY);
        } else {
            animationRenderer.skipToFirstFrame();
        }

        movePlayer(deltaX, deltaY);
        animationRenderer.update(delta);
    }

    public void movePlayer(float deltaX, float deltaY) {
        worldX += deltaX;
        worldY += deltaY;

        setLocation((int) worldX, (int) worldY);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        animationRenderer.render(graphics2D, getWidth(), getHeight());
    }

    @Override
    public void onCollision(CollisionListener other) {}

    @Override
    public Rectangle2D getBoundRect() {
        return getBounds().getBounds2D();
    }
}