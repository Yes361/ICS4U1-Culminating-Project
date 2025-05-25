import java.util.ArrayList;

abstract public class GameObject implements IGameObject {
    protected GameObject parent;
    protected ArrayList<GameObject> children = new ArrayList<>();
    protected String Identifier;

    public GameObject() {}

    abstract public void ready();
    abstract public void update(float delta);
    public void ExitTree() {};
    public void EnterTree() {};

    protected void UpdateHandler(float delta) {
        update(delta);

        for (GameObject child : children) {
            child.UpdateHandler(delta);
        }
    }

    protected void ReadyHandler() {}

    public IGameObject getParentNode() {
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