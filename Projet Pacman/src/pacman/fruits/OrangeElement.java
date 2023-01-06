package pacman.fruits;

import graphics.FixedImage;
import pacman.BonusElement;
import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;

public class OrangeElement extends FruitPowerElement {

    public OrangeElement (int startOfX, int startOfY, int baseSize) {

        FixedImage orangeImage = new FixedImage((int)(startOfX + 13.5 * baseSize), startOfY + 17 * baseSize, baseSize, baseSize, "assets/spriteTables/bonus/fruits/orange.png");

        PhysicalElement orangePhysical = new PhysicalElement(new Coordinates(orangeImage.getX(), orangeImage.getY()));
        orangePhysical.setBox(new CollisionBox(orangePhysical.getCoordinates(), baseSize, baseSize, false));
        setPhysicalElement(orangePhysical);

        orangeImage.scale(2);
        getGraphicalElement().makeImage(orangeImage);

        setScoreIncrementMultiplier(50);

    }
}