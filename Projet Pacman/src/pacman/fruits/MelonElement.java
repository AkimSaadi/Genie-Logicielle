package pacman.fruits;

import graphics.FixedImage;
import pacman.BonusElement;
import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;

public class MelonElement extends FruitPowerElement {

    public MelonElement (int startOfX, int startOfY, int baseSize) {

        FixedImage melonImage = new FixedImage((int)(startOfX + 13.5 * baseSize), startOfY + 17 * baseSize, baseSize, baseSize, "assets/spriteTables/bonus/fruits/melon.png");

        PhysicalElement melonPhysical = new PhysicalElement(new Coordinates(melonImage.getX(), melonImage.getY()));
        melonPhysical.setBox(new CollisionBox(melonPhysical.getCoordinates(), baseSize, baseSize, false));
        setPhysicalElement(melonPhysical);

        melonImage.scale(2);
        getGraphicalElement().makeImage(melonImage);

        setScoreIncrementMultiplier(100);

    }
}