package pacman.fruits;

import graphics.FixedImage;
import pacman.BonusElement;
import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;

public class KeyElement extends FruitPowerElement {

    public KeyElement (int startOfX, int startOfY, int baseSize) {

        FixedImage keyImage = new FixedImage((int)(startOfX + 13.5 * baseSize), startOfY + 17 * baseSize, baseSize, baseSize, "assets/spriteTables/bonus/fruits/key.png");

        PhysicalElement keyPhysical = new PhysicalElement(new Coordinates(keyImage.getX(), keyImage.getY()));
        keyPhysical.setBox(new CollisionBox(keyPhysical.getCoordinates(), baseSize, baseSize, false));
        setPhysicalElement(keyPhysical);

        keyImage.scale(2);
        getGraphicalElement().makeImage(keyImage);

        setScoreIncrementMultiplier(500);

    }
}