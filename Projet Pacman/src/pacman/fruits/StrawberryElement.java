package pacman.fruits;

import graphics.FixedImage;
import pacman.BonusElement;
import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;

public class StrawberryElement extends FruitPowerElement {

    public StrawberryElement (int startOfX, int startOfY, int baseSize) {

        FixedImage strawberryImage = new FixedImage((int)(startOfX + 13.5 * baseSize), startOfY + 17 * baseSize, baseSize, baseSize, "assets/spriteTables/bonus/fruits/strawberry.png");

        PhysicalElement strawberryPhysical = new PhysicalElement(new Coordinates(strawberryImage.getX(), strawberryImage.getY()));
        strawberryPhysical.setBox(new CollisionBox(strawberryPhysical.getCoordinates(), baseSize, baseSize, false));
        setPhysicalElement(strawberryPhysical);

        strawberryImage.scale(2);
        getGraphicalElement().makeImage(strawberryImage);

        setScoreIncrementMultiplier(30);

    }
}
