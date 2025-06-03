package Core.GameSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class JGameObject extends JComponent {
    protected JGameObject parent;
    protected List<JGameObject> children = new ArrayList<>();

    public JGameObject() {}

    public void update(float delta) {

    };

    public void UpdateHandler(float delta) {
        update(delta);

        for (JGameObject child : children) {
            child.UpdateHandler(delta);
        }
    }

    public JGameObject getParentNode() {
        return parent;
    }

    private void setParentNode(JGameObject parent) {
        this.parent = parent;
    }


    public void addChildExcludeRender(JGameObject gameObject) {
        children.add(gameObject);

        gameObject.setParentNode(this);
    }

    public void addChild(JGameObject gameObject) {
        children.add(gameObject);

        gameObject.setParentNode(this);

        super.add(gameObject);
    }

    public JGameObject getChild(int index) {
        return children.get(index);
    }

    public <T> T getChild(Class<T> classType) {
        for (JGameObject child : children) {
            if (classType.isInstance(child)) {
                return classType.cast(child);
            }
        }
        return null;
    }

    public <T> List<T> getChildren(Class<T> classType) {
        List<T> childrenClassType = new ArrayList<T>();
        for (JGameObject child : children) {
            if (classType.isInstance(child)) {
                childrenClassType.add(classType.cast(child));
            }
        }
        return childrenClassType;
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

        // TODO: if added by addChildExcludeRender
        super.remove(gameObject);
    }

//    public GameObject findNode() {
//        return null;
//    }
}