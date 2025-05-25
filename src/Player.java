import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends JPanel implements IGameObject {
    AnimationRenderer animationRenderer;

    private final MultiKeyListener KeyListener = new MultiKeyListener() {
        @Override
        public void MultiKeyTyped(KeyEvent[] keyEvents) {

        }

        @Override
        public void MultiKeyPressed(KeyEvent[] keyEvents) {

        }

        @Override
        public void KeyReleased(KeyEvent e) {

        }
    };

    public Player() {
//        super("I am a player!");

        addKeyListener(KeyListener);

        AnimationSprite idle = new AnimationSprite(
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Untitled design.jpg",
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Untitled design (1).jpg",
            "C:\\Users\\NAZRU\\IdeaProjects\\ICS4U1-Culminating-Project\\src\\Resources\\Sprites\\Untitled design (2).jpg"
        );

        AnimationSprites sprites = new AnimationSprites();
        sprites.addAnimation("idle", idle);

        animationRenderer = new AnimationRenderer(this, sprites);

//        System.out.println(animationRenderer.)
    }

    public void update(float delta) {
        int x = getX();
        int y = getY();
        float speed = 2;

        if (KeyListener.isKeyPressed(KeyEvent.VK_UP)) {
            y -= (int) (speed * delta);
        }

        if (KeyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
            y += (int) (speed * delta);
        }

        if (KeyListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= (int) (speed * delta);
        }

        if (KeyListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += (int) (speed * delta);
        }

        setLocation(x, y);

        animationRenderer.update(delta);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        animationRenderer.render(graphics, 50, 50);
    }
}
