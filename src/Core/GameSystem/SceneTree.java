package Core.GameSystem;

public class SceneTree {
    GameObject root;

    public SceneTree(GameObject root) {
        this.root = root;
        root.ReadyHandler();
    }

    public GameObject getRoot() {
        return root;
    }

    public void updateLoop() {
        double pastTimeMillis = System.currentTimeMillis();
        while (true) {
            double currentTimeMillis = System.currentTimeMillis();
            root.update((float) (currentTimeMillis - pastTimeMillis));
            pastTimeMillis = currentTimeMillis;
        }
    }
}
