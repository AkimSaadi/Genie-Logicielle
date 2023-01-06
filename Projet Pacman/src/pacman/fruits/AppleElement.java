package pacman.fruits;

import graphics.FixedImage;
import pacman.BonusElement;
import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;

public class AppleElement extends FruitPowerElement {

    public AppleElement (int startOfX, int startOfY, int baseSize) {

        FixedImage appleImage = new FixedImage((int)(startOfX + 13.5 * baseSize), startOfY + 17 * baseSize, baseSize, baseSize, "assets/spriteTables/bonus/fruits/apple.png");

        PhysicalElement applePhysical = new PhysicalElement(new Coordinates(appleImage.getX(), appleImage.getY()));
        applePhysical.setBox(new CollisionBox(applePhysical.getCoordinates(), baseSize, baseSize, false));
        setPhysicalElement(applePhysical);

        appleImage.scale(2);
        getGraphicalElement().makeImage(appleImage);

        setScoreIncrementMultiplier(70);

    }
}
