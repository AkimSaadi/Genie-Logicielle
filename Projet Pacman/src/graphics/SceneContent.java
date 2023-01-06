package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class SceneContent {

    private JPanel panel = new JPanel();

    public void SceneContent() {

        panel.setLayout(null);

        panel.setLocation(0, 0);

        panel.setBackground(Color.DARK_GRAY);

        panel.setVisible(true);
        panel.setFocusable(true);
    }

    protected JPanel getPanel() {
        return panel;
    }

    public void setBackground (Color color) {
        panel.setBackground(color);
    }

    public void add(GraphicalElement graphicalElement) {

        if (graphicalElement != null)
            if (graphicalElement.getComponent() != null)
                panel.add(graphicalElement.getComponent());

    }

    protected void replaceOrAdd(Component oldComponent, Component newComponent) {

        if (oldComponent == null) {
            panel.add(newComponent);
            return;
        }

        Component[] components = panel.getComponents();

        if (Arrays.asList(components).contains(oldComponent)) {
            int index = Arrays.asList(components).indexOf(oldComponent);

            panel.remove(oldComponent);
            panel.add(newComponent);
            panel.setComponentZOrder(newComponent, index);
        }

        panel.validate();
    }

    public void enable() {
        panel.setVisible(true);
        panel.setEnabled(true);
    }

    public void disable() {
        panel.setVisible(false);
        panel.setEnabled(false);
    }


    public void addKeyListener (KeyListener listener) {
        panel.addKeyListener(listener);
    }

    public KeyListener[] getKeyListeners() {
        return panel.getKeyListeners();
    }

    public Color getBackground() {
        return panel.getBackground();
    }
}
