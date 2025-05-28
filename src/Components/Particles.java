package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

class Particle {
    Point2D position;
    Point2D direction;
    float radius;

    public Particle(Point2D position, Point2D direction, float radius) {
        this.position = position;
        this.direction = direction;
        this.radius = radius;
    }

    public Point2D position() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        position.setLocation(x, y);
    }

    public Point2D direction() {
        return direction;
    }

    public float radius() {
        return radius;
    }
}

public class Particles {
    private Point2D center;
    private List<Particle> particles = new ArrayList<>();
    private float value = 0;
    private JComponent componentReference;

    public Particles(JComponent componentReference) {
        this.componentReference = componentReference;
    }

    public void kaboom(Point2D center) {
        this.center = center;
        particles.clear();

        value = 0;
        float radius = 0.1f;

        particles.add(new Particle(center, new Point2D.Float(-1, 0), radius));
        particles.add(new Particle(center, new Point2D.Float(1, 0), radius));
        particles.add(new Particle(center, new Point2D.Float(0, 1), radius));
        particles.add(new Particle(center, new Point2D.Float(0, -1), radius));
    }

    public void update(float delta) {
        value += delta;

        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            float x = (float) (particle.position().getX() + particle.direction().getX() * 2f);
            float y = (float) (particle.position().getY() + particle.direction().getY() * 2f);


            System.out.printf("%d, %f, %f, %f, %f\n", i, particle.position().getX(), particle.position().getY(), x, y);
            particle.position().setLocation(x, y);
        }
    }

    public void render(Graphics graphics) {
//        graphics.draw
        graphics.setColor(Color.RED);
        for (Particle particle : particles) {
            graphics.drawOval((int) particle.position().getX(), (int) particle.position().getY(), 10, 10);
        }

        componentReference.repaint();
    }
}
