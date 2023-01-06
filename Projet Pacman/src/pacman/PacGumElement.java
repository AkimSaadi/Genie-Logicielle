package pacman;

import physics.PhysicalElement;


public class PacGumElement extends BonusElement {

    private static final int basePacGumsAvailable = 238;
    private static int pacGumsAvailable = basePacGumsAvailable;

    public PacGumElement(PhysicalElement physicalElement) {
        super(physicalElement);
    }

    final int scoreIncrementMultiplier = 1;

    @Override
    public int getScoreIncrement() {
        return getBaseScoreIncrement();
    }

    public void eatPacGum() {
        pacGumsAvailable--;
    }

    public static boolean isAnyAvailable() {

        if (pacGumsAvailable == 0) return false;
        return true;
    }

    public static int howManyLeft() {
        return pacGumsAvailable;
    }

    public static void resetPacGumsAvailable() {
        pacGumsAvailable = basePacGumsAvailable;
    }
    public static void setPacGumsAvailable(int pacGumsAvailable) {
        PacGumElement.pacGumsAvailable = pacGumsAvailable;
    }

}
