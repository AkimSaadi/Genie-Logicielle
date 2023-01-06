package pacman.fruits;

import graphics.FixedImage;
import pacman.BonusElement;
import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;

public class BellElement extends FruitPowerElement {

    public BellElement (int startOfX, int startOfY, int baseSize) {

        FixedImage bellImage = new FixedImage((int)(startOfX + 13.5 * baseSize), startOfY + 17 * baseSize, baseSize, baseSize, "assets/spriteTables/bonus/fruits/bell.png");

        PhysicalElement bellPhysical = new PhysicalElement(new Coordinates(bellImage.getX(), bellImage.getY()));
        bellPhysical.setBox(new CollisionBox(bellPhysical.getCoordinates(), baseSize, baseSize, false));
        setPhysicalElement(bellPhysical);

        bellImage.scale(2);
        getGraphicalElement().makeImage(bellImage);

        setScoreIncrementMultiplier(300);

    }
}
