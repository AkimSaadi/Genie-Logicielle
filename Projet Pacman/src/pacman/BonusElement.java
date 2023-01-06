package pacman;

import core.SceneElement;
import physics.PhysicalElement;

public class BonusElement extends SceneElement {

    private final int baseScoreIncrement = 10;
    private int scoreIncrementMultiplier = 0;


    public BonusElement() {

    }

    public BonusElement(PhysicalElement physicalElement) {
        super(physicalElement);
    }

    public int getBaseScoreIncrement() {
        return baseScoreIncrement;
    }

    public int getScoreIncrement() {
        return scoreIncrementMultiplier * baseScoreIncrement;
    }

    public void setScoreIncrementMultiplier(int scoreIncrementMultiplier) {
        this.scoreIncrementMultiplier = scoreIncrementMultiplier;
    }

}
