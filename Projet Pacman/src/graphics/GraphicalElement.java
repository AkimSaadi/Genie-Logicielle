package graphics;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class GraphicalElement {

    private Object component = null;

    private boolean isVisibleWhenEnabled = true;

    private boolean isEnabled = true;

    private boolean canBeVisible = true;

    public void makeImage(Image image) {

        image.setLocation(image.getPoint().x, image.getPoint().y);

        component = image;
        refreshVisibility();

    }

    public void makeButton(String text, int x, int y, int width, int height) {

        JButton button = new JButton(text);


        button.setSize(width, height);
        button.setLocation(x, y);
        button.setEnabled(true);

        component = button;
        refreshVisibility();

    }

    public void makeRectangle(Point position, int width, int height, Color color) {
        JLabel label = new JLabel();

        label.setOpaque(true);

        label.setBackground(color);



        label.setLocation(position);


        label.setSize(width, height);

        component = label;

        refreshVisibility();
    }

    public void makeText(String text, Point point, int width, int height) {
        makeText(text, point, width, height, false);
    }

    public void makeText(String text, Point point, int width, int height, boolean transparent) {
        makeText(text, point, width, height, transparent, 255);
    }

    public void makeText(String text, Point point, int width, int height, boolean transparent, int textOpacity) {
        makeText(text,point, width, height, transparent, textOpacity, 12);
    }


    public void makeText(String text, Point point, int width, int height, boolean transparent, int textOpacity, float textSize) {

        JLabel textLabel = new JLabel(text, SwingConstants.CENTER);

        Font myFont = GraphicsMotor.getFont().deriveFont((float)textSize);

        textLabel.setFont(myFont);



        textLabel.setBackground(Color.WHITE);
        textLabel.setForeground(transparent ? new Color(255,255,255,textOpacity) : new Color(0,0,0,textOpacity));

        textLabel.setOpaque(!transparent);

        textLabel.setLocation(point);
        textLabel.setSize(width, height);

        component = textLabel;

        refreshVisibility();
    }

    public void makeText(String text, Point point, int width, int height, boolean transparent, int textOpacity, float textSize, SceneContent sceneContent) {

        JLabel textLabel = new JLabel(text, SwingConstants.CENTER);

        Font myFont = GraphicsMotor.getFont().deriveFont((float)textSize);

        textLabel.setFont(myFont);



        textLabel.setBackground(Color.WHITE);
        textLabel.setForeground(transparent ? new Color(255,255,255,textOpacity) : new Color(0,0,0,textOpacity));

        textLabel.setOpaque(!transparent);

        textLabel.setLocation(point);
        textLabel.setSize(width, height);

        Component previous;


        if (component instanceof Component || component == null)
            previous = (Component) component;
        else
            previous = (((Image) component).getJLabel());



        component = textLabel;

        refreshVisibility(previous, (Component) component, sceneContent);
    }

    public void makeSlider(int x, int y, int width, int height, int orientation, int minValue, int maxValue) {
        makeSlider(x, y, width, height, orientation, minValue, maxValue, 0);
    }

    public void makeSlider(int x, int y, int width, int height, int orientation, int minValue, int maxValue, int initialValue) {

        JSlider slider = new JSlider(orientation, minValue, maxValue, initialValue);

        slider.setBackground(Color.WHITE);
        slider.setForeground(Color.BLACK);

        slider.setOpaque(true);
        slider.setLocation(x, y);

        slider.setEnabled(false);

        slider.setSize(width, height);

        component = slider;

        refreshVisibility();

    }

    public void setEnabled (boolean isEnabled) {
        this.isEnabled = isEnabled;
        refreshVisibility();
    }

    public void setCanBeVisible (boolean canBeVisible) {
        this.canBeVisible = canBeVisible;

        refreshVisibility();
    }

    public void refreshVisibility(Component previous, Component newComponent, SceneContent sceneContent) {

        boolean visibility = isEnabled && isVisibleWhenEnabled || !isEnabled && !isVisibleWhenEnabled;

        visibility = canBeVisible && visibility;

        if (component instanceof JComponent)
            ((JComponent) component).setVisible(visibility);

        if (component instanceof Image)
            ((Image) component).getJLabel().setVisible(visibility);

        sceneContent.replaceOrAdd(previous, newComponent);

        if (previous instanceof JComponent)
            previous.setVisible(false);

        if (previous != null)
            previous.removeNotify();

        repaint();
    }

    public void refreshVisibility() {

        boolean visibility = isEnabled && isVisibleWhenEnabled || !isEnabled && !isVisibleWhenEnabled;

        visibility = canBeVisible && visibility;

        if (component instanceof JComponent)
            ((JComponent) component).setVisible(visibility);

        if (component instanceof Image)
            ((Image) component).getJLabel().setVisible(visibility);

        repaint();
    }

    public boolean isVisibleWhenEnabled() {
        return isVisibleWhenEnabled;
    }

    public void setVisibleWhenEnabled (boolean shouldBeVisibleWhenEnabled) {

        isVisibleWhenEnabled = shouldBeVisibleWhenEnabled;

        refreshVisibility();

    }

    public void rotate(double angle) {
        if (component instanceof AnimatedImage)
            ((AnimatedImage) component).rotate(angle - ((AnimatedImage) component).getAngle());
    }

    protected JComponent getComponent() {

        if (component instanceof Image)
            return ((Image) component).getJLabel();

        return (JComponent) component;

    }

    public boolean isAnimatedImage() {

        return (component != null) && (component instanceof AnimatedImage);

    }

    public AnimatedImage getAnimatedImage() {

        if (component instanceof AnimatedImage)
            return (AnimatedImage)  component;

        return null;

    }

    public void setLocation(Point location) {

        if (component == null)
            return;

        if (component instanceof Image) {
            ((Image) component).getJLabel().setLocation(location);
            return;
        }

        ((JComponent) component).setLocation(location);

    }

    public void tryToRefreshAnimation() {

        if (!isAnimatedImage())
            return;

        AnimatedImage animation = getAnimatedImage();

        if (System.currentTimeMillis() - animation.getLastPainted() > animation.getMillisecondsPerPaint()) {
            if (animation.checkRefreshAnimationFlag()) {
                animation.refreshSprite();
            }
        }

    }

    public void repaint() {

        if (component instanceof Image) {
            ((Image) component).getJLabel().repaint();
            return;
        }

        if (component != null)
            ((JComponent) component).repaint();

    }

    public ActionListener[] getActionListeners(){

        if(!(component instanceof JButton))
            return null;

        return ((JButton) component).getActionListeners();
    }


    public void addActionListener(ActionListener listener) {

        if(!(component instanceof JButton))
            return;

        ((JButton) component).addActionListener(listener);

    }
}
