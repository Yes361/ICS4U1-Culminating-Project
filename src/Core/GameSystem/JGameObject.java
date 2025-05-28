package Core.GameSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

abstract public class JGameObject extends JComponent {
    protected JGameObject parent;
    protected List<JGameObject> children = new ArrayList<>();

    public JGameObject() {}

    abstract public void ready();
    abstract public void update(float delta);

    protected void UpdateHandler(float delta) {
        update(delta);

        for (JGameObject child : children) {
            child.UpdateHandler(delta);
        }
    }

    public void ReadyHandler() {
        for (JGameObject child : children) {
            child.ReadyHandler();
        }

        ready();
    }

    public JGameObject getParentNode() {
        return parent;
    }

    private void setParentNode(JGameObject parent) {
        this.parent = parent;
    }

    public void addChild(JGameObject gameObject) {
        children.add(gameObject);

        gameObject.setParentNode(this);
    }

    public JGameObject getChild(int index) {
        return children.get(index);
    }

    public <T extends GameObject> T getChild(Class<T> classType) {
        for (JGameObject child : children) {
            if (classType.isInstance(child)) {
                return classType.cast(child);
            }
        }
        return null;
    }

    public int getChildCount() {
        return children.size();
    }

    public JGameObject[] getGameObjects() {
        return children.toArray(new JGameObject[0]);
    }

    public void removeChild(JGameObject gameObject) {
        children.remove(gameObject);

        gameObject.setParentNode(null);
    }

//    public GameObject findNode() {
//        return null;
//    }
}