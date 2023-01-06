package pacman.characters;

import core.SceneElement;
import physics.PhysicalElement;

public class CharacterElement extends SceneElement {

    public static final int DIRECTION_RIGHT = 0;
    public static final int DIRECTION_DOWN = 1;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_UP = 3;

    public static final int NO_DIRECTION = -1;


    protected int currentDirection = NO_DIRECTION;
    protected int wantedDirection = NO_DIRECTION;

    public CharacterElement() {
        super();
    }

    public CharacterElement(PhysicalElement physicalElement) {
        super(physicalElement);
    }


    public CharacterElement(boolean isEnabled) {
        super(isEnabled);
    }

    public CharacterElement(PhysicalElement physicalElement, boolean isEnabled) {
        super(physicalElement, isEnabled);
    }


    protected int getCurrentDirection() {
        return currentDirection;
    }

    protected void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }

    protected int getWantedDirection() {
        return wantedDirection;
    }

    protected void setWantedDirection(int wantedDirection) {
        this.wantedDirection = wantedDirection;
    }
}
