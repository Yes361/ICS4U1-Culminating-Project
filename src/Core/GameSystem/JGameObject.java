package Core.GameSystem;

abstract public class JGameObject extends GameObject {
    protected JGameComponent JRenderer;

    public JGameObject(JGameComponent JRenderer) {
        super();

        this.JRenderer = JRenderer;
    }

    public void setJRenderer(JGameComponent JRenderer) {
        this.JRenderer = JRenderer;
    }

    public JGameComponent getJRenderer() {
        return JRenderer;
    }
}
