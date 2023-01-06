package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;




import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class AnimatedImage extends Image {

    private BufferedImage originalSpritesTable;

    private ArrayList<BufferedImage> spritesList = new ArrayList<>();

    private ArrayList<ImageIcon> imageIconList = new ArrayList<>();

    private long paintPerSecond;

    private long millisecondsPerPaint;

    private int spritesWidth;
    private int spritesHeight;

    private double angle = 0;

    private int cyclicSpriteIndex = 0;

    private long lastPainted = System.currentTimeMillis();

    private boolean refreshAnimationFlag = false;


    public AnimatedImage(int x, int y, int spritesWidth, int spritesHeight, int paintPerSecond, String fileName) {

        super(x, y);

        this.paintPerSecond = paintPerSecond;
        millisecondsPerPaint = (long) ((1.0 / (double) (paintPerSecond)) * 1000.0);


        this.spritesWidth = spritesWidth;
        this.spritesHeight = spritesHeight;


        instantiateBufferedImageFromFile(fileName);


        instantiateFromSpriteTable();

        getJLabel().setSize(new Dimension(spritesWidth, spritesHeight));

        getJLabel().setIcon(imageIconList.get(cyclicSpriteIndex));

    }


    public void instantiateFromSpriteTable() {

        splitSpriteTable();
        splitSpritesTableIntoImageIcons();

    }


    public void splitSpriteTable() {
        if (spritesList != null)
            spritesList.clear();

        for (int i = 0; i * spritesWidth < originalSpritesTable.getWidth(); i++)

            spritesList.add(
                    originalSpritesTable.getSubimage(
                            i * spritesWidth, 0, spritesWidth, spritesHeight
                    )
            );
    }


    public void splitSpritesTableIntoImageIcons() {
        if (imageIconList != null)
            imageIconList.clear();

        for (BufferedImage sprite : spritesList)
            imageIconList.add(new ImageIcon(sprite));
    }


    public void scale (double scale) {

        getJLabel().setSize((int) (getWidth() * scale), (int) (getHeight() * scale));

        spritesWidth  = (int) (spritesWidth  * scale);
        spritesHeight = (int) (spritesHeight * scale);

        BufferedImage scaledSpritesTable = new BufferedImage((int) (originalSpritesTable.getWidth() * scale), (int) (originalSpritesTable.getHeight() * scale), BufferedImage.TYPE_INT_ARGB);

        AffineTransform scaleInstance = AffineTransform.getScaleInstance(scale, scale);

        AffineTransformOp scaleOp = new AffineTransformOp(scaleInstance, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);


        scaleOp.filter(originalSpritesTable, scaledSpritesTable);

        originalSpritesTable = scaledSpritesTable;

        splitSpriteTable();
        splitSpritesTableIntoImageIcons();


        setImageIcon(imageIconList.get(cyclicSpriteIndex));

    }


    public void rotate (double angle) {

        this.angle += angle;

        ArrayList<BufferedImage> rotatedSpriteList = new ArrayList<>();


        for (BufferedImage sprite : spritesList) {

            BufferedImage rotatedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);


            AffineTransform rotateTransform = new AffineTransform();

            rotateTransform.rotate(angle, spritesWidth / 2, spritesHeight / 2);

            AffineTransformOp scaleOp = new AffineTransformOp(rotateTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

            scaleOp.filter(sprite, rotatedSprite);


            rotatedSpriteList.add(rotatedSprite);

        }

        spritesList.clear();
        spritesList = rotatedSpriteList;

        splitSpritesTableIntoImageIcons();

        setImageIcon(imageIconList.get(cyclicSpriteIndex));

    }


    public void refreshSprite() {

        cyclicSpriteIndex++;


        if (cyclicSpriteIndex == imageIconList.size())
            cyclicSpriteIndex = 0;


        setImageIcon(imageIconList.get(cyclicSpriteIndex));
        lastPainted = System.currentTimeMillis();

    }


    public double getAngle() {
        return angle;
    }

    public long getLastPainted() {
        return lastPainted;
    }

    public long getMillisecondsPerPaint() {
        return millisecondsPerPaint;
    }

    public boolean checkRefreshAnimationFlag() {

        boolean flag = refreshAnimationFlag;

        refreshAnimationFlag = false;

        return flag;
    }

    public void setRefreshAnimationFlag(boolean refreshAnimationFlag) {

        this.refreshAnimationFlag = refreshAnimationFlag;

    }


    public void setOriginalSpritesTable(BufferedImage originalSpritesTable) {
        this.originalSpritesTable = originalSpritesTable;
    }


    private void instantiateBufferedImageFromFile (String fileName) {

        // Cette URL est un adresse qui doit commencer par "jar:" si on est dans un .jar
        URL testInJar = AnimatedImage.class.getResource(fileName);


        InputStream inputStream = AnimatedImage.class.getResourceAsStream(fileName);


        if (testInJar == null && !fileName.startsWith("/"))
            testInJar = AnimatedImage.class.getResource("/".concat(fileName));


        if (inputStream == null && !fileName.startsWith("/"))
            inputStream = AnimatedImage.class.getResourceAsStream("/".concat(fileName));


        if (inputStream != null)
            System.out.println(inputStream.toString());


        try {
            if (inputStream != null && testInJar.toString().startsWith("jar:"))
                setOriginalSpritesTable(ImageIO.read(inputStream));
            else
                setOriginalSpritesTable(ImageIO.read(new File(fileName)));
            return;
        }
        catch (IOException e) {
            System.exit(1);
        }
        setOriginalSpritesTable(new BufferedImage(0,0,0));
    }


}