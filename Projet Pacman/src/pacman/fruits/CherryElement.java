package pacman.fruits;

import graphics.FixedImage;
import pacman.BonusElement;
import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;

public class CherryElement extends FruitPowerElement {

    public CherryElement (int startOfX, int startOfY, int baseSize) {

        FixedImage cherryImage = new FixedImage((int)(startOfX + 13.5 * baseSize), startOfY + 17 * baseSize, baseSize, baseSize, "assets/spriteTables/bonus/fruits/cherry.png");

        PhysicalElement cherryPhysical = new PhysicalElement(new Coordinates(cherryImage.getX(), cherryImage.getY()));
        cherryPhysical.setBox(new CollisionBox(cherryPhysical.getCoordinates(), baseSize, baseSize, false));
        setPhysicalElement(cherryPhysical);

        cherryImage.scale(2);
        getGraphicalElement().makeImage(cherryImage);

        setScoreIncrementMultiplier(10);

    }
}
