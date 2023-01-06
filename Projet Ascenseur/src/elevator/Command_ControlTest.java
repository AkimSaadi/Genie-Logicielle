package elevator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


 class Command_ControlTest{

    private Command_Control commandControl;

     @BeforeEach
     void setUp() {
         commandControl = new Command_Control(10, new Operative_Part(4,58));
     }

     @AfterEach
     void tearDown() {
         commandControl=null;
     }


    /**
     * <p> Test de la fonction addDestination.
     * </p>
     */
    @Test
    void addDestination() {
        commandControl.setCurrentDestination(2);
        commandControl.addDestination(3);
        commandControl.addDestination(3);
        assertEquals(1, Collections.frequency(commandControl.getNextDestinations(),3));
        commandControl.getNextDestinations().clear();
        commandControl.getOperative_part().setGoingUp(true);
        commandControl.setCurrentDestination(6);
        commandControl.setCurrentLevel(4);
        commandControl.addDestination(5);
        assertTrue(commandControl.getNextDestinations().contains(6));
        assertEquals(5,commandControl.getCurrentDestination());
        commandControl.getNextDestinations().clear();
        commandControl.getOperative_part().setGoingUp(true);
        commandControl.setCurrentDestination(4);
        commandControl.setCurrentLevel(3);
        commandControl.addDestination(5);
        commandControl.addDestination(7);
        commandControl.addDestination(2);
        commandControl.addDestination(1);
        commandControl.addDestination(6);
        assertEquals(6,commandControl.getNextDestinations().get(1));
        commandControl.getNextDestinations().clear();
        commandControl.getOperative_part().setGoingUp(true);
        commandControl.setCurrentDestination(5);
        commandControl.setCurrentLevel(4);
        commandControl.addDestination(6);
        commandControl.addDestination(2);
        commandControl.addDestination(1);
        commandControl.addDestination(3);
        assertEquals(3,commandControl.getNextDestinations().get(1));
        commandControl.getNextDestinations().clear();
        commandControl.getOperative_part().setGoingUp(false);
        commandControl.getOperative_part().setGoingUpStopsNext(false);
        commandControl.getOperative_part().setGoingDown(true);
        commandControl.setCurrentDestination(3);
        commandControl.setCurrentLevel(5);
        commandControl.addDestination(2);
        commandControl.addDestination(1);
        commandControl.addDestination(6);
        commandControl.addDestination(7);
        commandControl.addDestination(4);
        assertEquals(3,commandControl.getNextDestinations().get(0));
        assertEquals(4,commandControl.getCurrentDestination());
        commandControl.getNextDestinations().clear();
        commandControl.getOperative_part().setGoingDown(true);
        commandControl.setCurrentDestination(4);
        commandControl.setCurrentLevel(7);
        commandControl.addDestination(1);
        commandControl.addDestination(3);
        commandControl.addDestination(8);
        commandControl.addDestination(9);
        commandControl.addDestination(2);
        assertEquals(2,commandControl.getNextDestinations().get(1));
        commandControl.getNextDestinations().clear();
        commandControl.getOperative_part().setGoingDown(true);
        commandControl.setCurrentDestination(4);
        commandControl.setCurrentLevel(4);
        commandControl.addDestination(3);
        commandControl.addDestination(2);
        commandControl.addDestination(6);
        commandControl.addDestination(8);
        commandControl.addDestination(7);
        assertEquals(7,commandControl.getNextDestinations().get(2));
        commandControl.getNextDestinations().clear();
        commandControl.getOperative_part().setGoingDown(false);
        commandControl.getOperative_part().setGoingDownStopsNext(false);
        commandControl.getOperative_part().setStopped(true);
        commandControl.setCurrentLevel(3);
        commandControl.addDestination(4);
        assertEquals(4,commandControl.getCurrentDestination());
    }

    /**
     * <p> Test de la fonction refreshCurrentDestination.
     * </p>
     */
    @Test
    void refreshCurrentDestination() {
        commandControl.addDestination(2);
        commandControl.setCurrentLevel(3);
        commandControl.setCurrentDestination(3);
        commandControl.refreshCurrentDestination();
        assertEquals(2, commandControl.getCurrentDestination());
    }

    /**
     * <p> Test de la fonction refreshCurrentLevel.
     * </p>
     */
    @Test
    public void refreshCurrentLevel() {
        commandControl.setCurrentLevel(2);
        commandControl.setCurrentDestination(4);
        commandControl.refreshCurrentLevel();
        assertEquals(3, commandControl.getCurrentLevel());
        commandControl.setCurrentLevel(2);
        commandControl.setCurrentDestination(0);
        commandControl.refreshCurrentLevel();
        assertEquals(1, commandControl.getCurrentLevel());
    }

    /**
     * <p> Test de la fonction refreshStep.
     * </p>
     */

    @Test
    public void refreshStep() {

        commandControl.setCurrentLevel(4);
        commandControl.addDestination(2);
        commandControl.setCurrentDestination(1);
        commandControl.refreshStep();
        assertTrue(commandControl.getOperative_part().isGoingDown());
        commandControl.getOperative_part().setGoingDown(false);
        commandControl.getOperative_part().setStopped(true);
        commandControl.setCurrentLevel(0);
        commandControl.setCurrentDestination(4);
        commandControl.refreshStep();
        assertTrue(commandControl.getOperative_part().isGoingUp());
        commandControl.setCurrentLevel(0);
        assertTrue(commandControl.getOperative_part().isSignalDownLimit());
        commandControl.setCurrentLevel(2);
        commandControl.setCurrentDestination(3);
        commandControl.refreshStep();
        assertTrue(commandControl.getOperative_part().isSignalUpLimit());

    }

    /**
     * <p> Test de la fonction emergencyStop.
     * </p>
     */
    @Test
    void emergencyStop() {
        commandControl.emergencyStop();
        assertTrue(commandControl.getOperative_part().isEmergencyStopped());
    }
    /**
     * <p> Test de la fonction getCurrentDestination.
     * </p>
     */
    @Test
    void getCurrentDestination() {
        commandControl.setCurrentDestination(2);
        assertEquals(2,commandControl.getCurrentDestination());
    }
    /**
     * <p> Test de la fonction getCurrentLevel.
     * </p>
     */
    @Test
    void getCurrentLevel() {
        commandControl.setCurrentLevel(2);
        assertEquals(2,commandControl.getCurrentLevel());
    }
}