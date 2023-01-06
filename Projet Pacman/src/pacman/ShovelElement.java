package pacman;

import graphics.AnimatedImage;
import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;

public class ShovelElement extends BonusElement {
    final int shovelAnimationPaintPerSecond = 8;

    public ShovelElement(Coordinates coordinates, int baseSize) {
        AnimatedImage shovelImage = new AnimatedImage((int) coordinates.getX(), (int) coordinates.getY(), baseSize, baseSize, shovelAnimationPaintPerSecond, "assets/spriteTables/bonus/powers/shovel.png");
        PhysicalElement shovelPhysical = new PhysicalElement(new Coordinates(shovelImage.getX(), shovelImage.getY()));
        shovelPhysical.setBox(new CollisionBox(shovelPhysical.getCoordinates(), baseSize, baseSize, false));
        setPhysicalElement(shovelPhysical);
        shovelImage.scale(2);
        getGraphicalElement().makeImage(shovelImage);

    }
}
