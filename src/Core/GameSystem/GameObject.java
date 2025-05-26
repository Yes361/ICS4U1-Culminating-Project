package Core.GameSystem;

import java.util.*;

abstract public class GameObject {
    protected GameObject parent;
    protected List<GameObject> children = new ArrayList<>();
    protected List<GameComponent> components = new ArrayList<>();
    protected String Identifier;

    public GameObject() {}

    abstract public void ready();
    abstract public void update(float delta);
    public void ExitTree() {};
    public void EnterTree() {};

    public void addComponent(GameComponent component) {
        component.setRef(this);
        components.add(component);
    }

    public GameComponent getComponent(int index) {
        return components.get(index);
    }

    public <T extends GameComponent> T getComponent(Class<T> classType) {
        for (GameComponent component : components) {
            if (classType.isInstance(component)) {
                return classType.cast(component);
            }
        }
        return null;
    }

    public void removeComponent(GameComponent component) {
        component.setRef(null);
        components.remove(component);
    }

    public void removeComponent(int index) {
        components.remove(index);
    }

    protected void UpdateHandler(float delta) {
        update(delta);

        for (GameObject child : children) {
            child.UpdateHandler(delta);
        }

        for (GameComponent component : components) {
            component.update(delta);
        }
    }

    public void ReadyHandler() {
        for (GameObject child : children) {
            child.ReadyHandler();
        }

        ready();
    }

    public GameObject getParentNode() {
        return parent;
    }

    private void setParentNode(GameObject parent) {
        this.parent = parent;
    }

    public void addChild(GameObject gameObject) {
        children.add(gameObject);

        gameObject.setParentNode(this);
        gameObject.EnterTree();
    }

    public GameObject getChild(int index) {
        return children.get(index);
    }

    public <T extends GameObject> T getChild(Class<T> classType) {
        for (GameObject child : children) {
            if (classType.isInstance(child)) {
               return classType.cast(child);
            }
        }
        return null;
    }

    public int getChildCount() {
        return children.size();
    }

    public GameObject[] getGameObjects() {
        return children.toArray(new GameObject[0]);
    }

    public void removeChild(GameObject gameObject) {
        children.remove(gameObject);

        gameObject.setParentNode(null);
        gameObject.ExitTree();
    }

    public GameObject findNode() {
        return null;
    }

    public String getIdentifier() {
        return Identifier;
    }
}