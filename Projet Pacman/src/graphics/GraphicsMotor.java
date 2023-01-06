package graphics;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class GraphicsMotor {

    private static JFrame frame = new JFrame();

    //Marge du haut pour afficher le score
    public static int upperMargin;
    public static int lowerMargin;

    private int width = 448;
    private int height = 496;

    private static Font font = null;


    public GraphicsMotor (String title, String fileName, int upperMargin, int lowerMargin) {

        JPanel panel = new JPanel();

        this.upperMargin = upperMargin;
        this.lowerMargin = lowerMargin;

        height += upperMargin+lowerMargin;

        if (getFont() == null)
            setFont(instantiateFontFromFile("assets/Joystix.ttf"));

        ImageIcon icon = initializeIcon(fileName);
        buildFrame(title, icon, panel);

    }

    private void buildFrame (String title, ImageIcon icon, JPanel panel) {

        frame.setTitle(title);


        frame.setResizable(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        panel.setLayout(null);

        panel.setLocation(0, 0);

        panel.setBackground(Color.DARK_GRAY);

        panel.setVisible(true);
        panel.setFocusable(true);

        panel.setPreferredSize(new Dimension(width, height));

        frame.setContentPane(panel);

        frame.pack();


        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        if (icon != null)
            frame.setIconImage(icon.getImage());

    }


    private Font instantiateFontFromFile (String fileName) {

        Font myFont = new Font("Courier", Font.PLAIN, 20);

        URL resource = GraphicsMotor.class.getResource(fileName);

        if (resource == null)
            if (!fileName.startsWith("/"))
                resource = GraphicsMotor.class.getResource("/".concat(fileName));


        if (resource != null)
            System.out.println(resource.toString());

        if (resource != null && resource.toString().startsWith("jar:")) {

            if (!fileName.startsWith("/"))
                fileName = "/".concat(fileName);

            InputStream in = getClass().getResourceAsStream(fileName);


            try {
                myFont = Font.createFont(Font.TRUETYPE_FONT, in);
            }
            catch (IOException | FontFormatException e) {
                System.out.println(e);
                System.out.println("Erreur, police d'écriture introuvable");
            }

        } else {


            File file = new File(fileName);

            try {
                myFont = Font.createFont(Font.TRUETYPE_FONT, file);
            }
            catch (IOException | FontFormatException e) {
                System.out.println(e);
                System.out.println("Erreur, police d'écriture introuvable");
            }
        }


        return myFont;

    }


    public ImageIcon initializeIcon(String fileName) {

        try {
            URL resource = GraphicsMotor.class.getResource(fileName);

            if (resource == null)
                if (!fileName.startsWith("/"))
                    resource = GraphicsMotor.class.getResource("/".concat(fileName));

            if (resource != null)
                System.out.println(resource.toString());

            if (resource != null && resource.toString().startsWith("jar:"))
                return new ImageIcon(resource);
            else
                return new ImageIcon(fileName);
        }
        catch (ClassCastException e) {}

        return null;
    }

    public void repaint() {
        frame.repaint();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public static Font getFont() {
        return font;
    }

    public static void setFont(Font newFont) {
        font = newFont;
    }


    public void setSceneContent(SceneContent sceneContent) {

        setPanel(sceneContent.getPanel());

    }

    private void setPanel(JPanel panel) {

        panel.setLayout(null);

        panel.setLocation(0, 0);

        panel.setVisible(true);

        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.requestFocus();
        panel.revalidate();

        panel.setPreferredSize(new Dimension(width, height));



        frame.setContentPane(panel);
        frame.getContentPane().requestFocus();
        frame.pack();

    }

}

