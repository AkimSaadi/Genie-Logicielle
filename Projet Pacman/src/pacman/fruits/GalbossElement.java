package pacman.fruits;

import graphics.FixedImage;
import pacman.BonusElement;
import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;

public class GalbossElement extends FruitPowerElement {

    public GalbossElement (int startOfX, int startOfY, int baseSize) {

        FixedImage galbossImage = new FixedImage((int)(startOfX + 13.5 * baseSize), startOfY + 17 * baseSize, baseSize, baseSize, "assets/spriteTables/bonus/fruits/galaxian boss.png");

        PhysicalElement galbossPhysical = new PhysicalElement(new Coordinates(galbossImage.getX(), galbossImage.getY()));
        galbossPhysical.setBox(new CollisionBox(galbossPhysical.getCoordinates(), baseSize, baseSize, false));
        setPhysicalElement(galbossPhysical);

        galbossImage.scale(2);
        getGraphicalElement().makeImage(galbossImage);

        setScoreIncrementMultiplier(200);

    }
}
