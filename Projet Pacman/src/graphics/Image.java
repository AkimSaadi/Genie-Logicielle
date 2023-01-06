package graphics;

import javax.swing.*;
import java.awt.*;



public class Image {



    private ImageIcon image = new ImageIcon();
    private Point position;
    private JLabel label;


    public Image(int x, int y) {
        label = new JLabel();

        position = new Point(x, y);

        label.setLocation((int) position.getX(), (int) position.getY());
        label.setVisible(true);
    }


    public void setVisible(Boolean visible) {
        label.setVisible(visible);
    }

    public void setLocation(Point point) {
        label.setLocation(point.x, point.y);
    }

    public void setLocation(int x, int y) {
        label.setLocation(x, y);
    }

    public Point getLocation() {
        return label.getLocation();
    }

    public int getHeight() {
        return label.getHeight();
    }

    public int getWidth() {
        return label.getWidth();
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public void setPosition(Point position) {
        this.position = new Point(position.x, position.y);
    }

    public void setPosition(int x, int y) {
        position = new Point(x, y);
    }

    protected ImageIcon getImageIcon() {
        return image;
    }

    protected void setImageIcon(ImageIcon image) {
        this.image = image;
        label.setIcon(image);
    }

    public Point getPoint() {
        return position;
    }

    public void setVisible() {
        label.setVisible(true);
    }

    public void setLocation() {
        label.setLocation(getPoint().x, getPoint().y);
    }

    protected JLabel getJLabel() {
        return label;
    }
}