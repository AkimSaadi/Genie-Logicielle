package graphics;


import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class FixedImage extends Image {


    public FixedImage(int x, int y, int width, int height, String fileName) {

        super(x, y);

        getJLabel().setSize(new Dimension(width, height));
        instantiateImageIconFromFile(fileName);

    }


    public void scale(double scale) {

        getJLabel().setSize((int) (getWidth() * scale), (int) (getHeight() * scale));

        ImageIcon imageIcon = super.getImageIcon();
        java.awt.Image scaledImage = imageIcon.getImage();

        imageIcon = new ImageIcon(scaledImage.getScaledInstance((int) (imageIcon.getIconWidth() * scale), (int) (imageIcon.getIconHeight() * scale), java.awt.Image.SCALE_SMOOTH));
        getJLabel().setIcon(imageIcon);

    }


    private void instantiateImageIconFromFile (String fileName) {

        URL resource = FixedImage.class.getResource(fileName);

        if (resource == null)
            if (!fileName.startsWith("/"))
                resource = FixedImage.class.getResource("/".concat(fileName));

        if (resource != null)
            System.out.println(resource.toString());

        if (resource != null && resource.toString().startsWith("jar:"))
            setImageIcon(new ImageIcon(resource));
        else
            setImageIcon(new ImageIcon(fileName));
    }


}