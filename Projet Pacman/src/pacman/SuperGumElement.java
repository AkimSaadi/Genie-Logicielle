package pacman;


import core.Core;

import graphics.AnimatedImage;

import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;


public class SuperGumElement extends BonusElement {


    public SuperGumElement(Coordinates coordinates, int baseSize, Core core) {

        super(new PhysicalElement(coordinates));

        final int animationsPerSecond = 6;

        AnimatedImage superPacGumImage = new AnimatedImage((int) coordinates.getX(), (int) coordinates.getY(), 16, 16, animationsPerSecond, "assets/spriteTables/bonus/powers/superPacgum.png");

        getPhysicalElement().setBox(new CollisionBox(new Coordinates(superPacGumImage.getX() - baseSize / 2, superPacGumImage.getY() - baseSize / 2), baseSize * 2, baseSize * 2, false));

        superPacGumImage.scale(2);


        setPhysicalElement(getPhysicalElement());
        getGraphicalElement().makeImage(superPacGumImage);


    }


}
