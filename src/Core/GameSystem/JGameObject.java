/*
* Authors: Raiyan Islam and Ahnaf Masud
*
* Description:
* JGameObject represents a JComponent that is part of the Game Engine's lifecycle
* It has references to children JGameObject's and propogates an "Update" signal
*
* Issues:
* The dichotomy between the Swing Component Life cycle and this Game engine isn't well established.
* A key point that we'd like to address is the seperation between logic and UI, JComponents are entirely meant
* to handle UI, however JGameObject's sometimes only handle logic.
*
* Things to work on:
* Fully fleshing out this class
*
*  */

package Core.GameSystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JGameObject extends JComponent {
    // Field Variables
    protected JGameObject parent; // Reference to the parent JGameObject

    // A list of the children
    protected List<JGameObject> children = new ArrayList<>();

    public JGameObject() {}

    /**
     * The update method is called every moment
     *
     * @param delta is the difference between when the update method is called
     */

    public void update(float delta) {};

    /**
     * UpdateHandler handles updating this class as well as its children
     *
     * @param delta is the difference between when the update method is called
     */
    public void UpdateHandler(float delta) {
        update(delta);

        // Iterating over children and calling their handlers
        for (JGameObject child : children) {
            child.UpdateHandler(delta);
        }

        // Iterating over JComponents that extend a JGameObjectInterface.
        // JGameObjectInterface enables JPanels and other implementations of JComponents
        // to implement update methods
        getComponentChildren(JGameObjectInterface.class).forEach(new Consumer<JGameObjectInterface>() {
            @Override
            public void accept(JGameObjectInterface gameObject) {
                gameObject.update(delta);
            }
        });
    }

    /**
     * GetParentNode returns the reference to this JGameObject
     */
    public JGameObject getParentNode() {
        return parent;
    }

    /**
     * setParentNode sets the JGameObject's parent
     *
     */
    private void setParentNode(JGameObject parent) {
        this.parent = parent;
    }

    /**
     * Adds child interally to update loop, but not to the UI
     *
     * @param gameObject the JGameObject added to this component
     */
    public void addChildExcludeRender(JGameObject gameObject) {
        children.add(gameObject);

        gameObject.setParentNode(this); // setting parent node of the added gameObject
    }

    /**
     * addChild adds a JGameObject to the UI and update Loop
     *
     * @param gameObject the JGameObject added to this component
     */
    public void addChild(JGameObject gameObject) {
        children.add(gameObject);

        gameObject.setParentNode(this); // setting reference

        // adding gameObject and revalidating layout
        super.add(gameObject);
        revalidate();
        repaint();
    }

    /**
     * getComponentChildren filters and returns the children by the specified classType
     *
     * @param classType the type to filter
     *
     * @return The list of children
     */
    public <T> List<T> getComponentChildren(Class<T> classType) {
        List<T> childrenClassType = new ArrayList<T>();

        for (Component child : getComponents()) {
            // Checking if the child matches the class type
            if (classType.isInstance(child)) {
                childrenClassType.add(classType.cast(child));
            }
        }

        return childrenClassType;
    }

    /**
     * getChild gets the children at a specified index
     *
     * @param index the index of the child to return
     *
     * @return a JGameObject
     */
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}