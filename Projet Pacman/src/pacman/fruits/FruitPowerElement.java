package pacman.fruits;

import pacman.BonusElement;
import physics.Coordinates;

public class FruitPowerElement extends BonusElement {

    protected long onsetTime = System.currentTimeMillis();
    private long disapperanceTime;
    private long waitBeforeRespawn = 25000;

    @Override
    public void disable() {
        super.disable();
        isEnabled = true;
        disapperanceTime = System.currentTimeMillis();
    }

    @Override
    public void enable() {
        super.enable();
        onsetTime = System.currentTimeMillis();

    }


    @Override
    public void refreshElementIfNeeded() {
        if (System.currentTimeMillis() > Long.sum(onsetTime,waitBeforeRespawn) && getPhysicalElement().isEnabled()) {
            disable();
        }
        else if (System.currentTimeMillis() > Long.sum(disapperanceTime,waitBeforeRespawn) && ! getPhysicalElement().isEnabled() ) {
            enable();
        }

    }
}
