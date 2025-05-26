package Core.GameSystem;

abstract public class GameComponent {
    GameObject ref;

    abstract public void update(float delta);

    public void setRef(GameObject ref) {
        this.ref = ref;
    }

    public GameObject getRef() {
        return ref;
    }
}
