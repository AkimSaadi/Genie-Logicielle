package pacman.characters;

import graphics.AnimatedImage;
import graphics.FixedImage;
import graphics.SceneContent;
import physics.PhysicalElement;


public class GhostCharacter extends CharacterElement {


    private boolean alive = true;

    private AnimatedImage regularImage = null;
    private static final int paintPerSecond = 6;

    private static AnimatedImage scaredBlinkingImage = new AnimatedImage(0, 0, 16, 16, paintPerSecond, "assets/spriteTables/ghosts/scaredBlinkingGhost.png");
    private static AnimatedImage scaredImage = new AnimatedImage(0, 0, 16, 16, paintPerSecond, "assets/spriteTables/ghosts/scaredGhost.png");
    public GhostCharacter() {
        super();
    }

    public GhostCharacter(PhysicalElement physicalElement) {
        super(physicalElement);

    }

    public GhostCharacter(boolean isEnabled) {
        super(isEnabled);

    }

    public GhostCharacter(PhysicalElement physicalElement, boolean isEnabled) {
        super(physicalElement, isEnabled);

    }

    public boolean isAlive() {
        return alive;
    }

    public void dead(){alive = false;}

    public void setRegularImage(AnimatedImage image) {
        regularImage = image;
        getGraphicalElement().makeImage(image);
    }

}