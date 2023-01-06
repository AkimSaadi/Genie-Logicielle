package elevator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Operative_PartTest {

    private Operative_Part operativePart;
   @BeforeEach
    void setUp() {
        operativePart = new Operative_Part(1,18.5);
    }

    @AfterEach
    void tearDown() {
        operativePart=null;
    }

 /**
  * <p> Test de la fonction refreshStep.
  * </p>
  */

    @Test
    void refreshStep() {
     operativePart.setGoingUp(true);
     operativePart.setCurrentHeightAsSteps(0);
     operativePart.refreshStep();
     assertEquals(1,operativePart.getCurrentHeightAsSteps());
     operativePart.setGoingUp(false);
     operativePart.setGoingDown(true);
     operativePart.setCurrentHeightAsSteps(1);
     operativePart.refreshStep();
     assertEquals(0,operativePart.getCurrentHeightAsSteps());
     operativePart.setSignalLevelChanged(true);
     operativePart.setGoingDownStopsNext(true);
     operativePart.refreshStep();
     assertTrue(operativePart.isStopped());
    }
 /**
  * <p> Test de la fonction signalLevelChanged.
  * </p>
  */
    @Test
    void signalLevelChanged() {
    operativePart.signalLevelChanged();
    assertTrue(operativePart.isSignalLevelChanged());
    }
 /**
  * <p> Test de la fonction signalUpLimit.
  * </p>
  */
    @Test
    void signalUpLimit() {
     operativePart.signalUpLimit();
     assertTrue(operativePart.isSignalUpLimit());
    }
 /**
  * <p> Test de la fonction signalDownLimit.
  * </p>
  */
    @Test
    void signalDownLimit() {
     operativePart.signalDownLimit();
     assertTrue(operativePart.isSignalDownLimit());
    }
 /**
  * <p> Test de la fonction goUp.
  * </p>
  */
    @Test
    void goUp() {
     operativePart.goUp();
     assertTrue(operativePart.isGoingUp());

    }
 /**
  * <p> Test de la fonction goDown.
  * </p>
  */
    @Test
    void goDown() {
     operativePart.goDown();
     assertTrue(operativePart.isGoingDown());
    }
 /**
  * <p> Test de la fonction stopNextLevel.
  * </p>
  */
    @Test
    void stopNextLevel() {
     operativePart.setGoingDown(true);
     operativePart.stopNextLevel();
     assertTrue(operativePart.isGoingDownStopsNext());
     operativePart.setGoingDown(false);
     operativePart.setGoingDownStopsNext(false);
     operativePart.setGoingUp(true);
     operativePart.stopNextLevel();
     assertTrue(operativePart.isGoingUpStopsNext());
    }
 /**
  * <p> Test de la fonction emergencyStop.
  * </p>
  */
    @Test
    void emergencyStop() {
     operativePart.emergencyStop();
     assertTrue(operativePart.isEmergencyStopped());
    }
 /**
  * <p> Test de la fonction setStepsToCurrentDestination.
  * </p>
  */
    @Test
    void setStepsToCurrentDestination() {
     operativePart.setStepsToCurrentDestination(3);
     assertEquals(operativePart.getNumberOfSteps()*3,operativePart.getStepsToCurrentDestination());
    }

 /**
  * <p> Test de la fonction getElevatorPosition.
  * </p>
  */
    @Test
    void getElevatorPosition() {
     assertEquals(0,operativePart.getElevatorPosition());
    }
 /**
  * <p> Test de la fonction isSignalLevelChanged
  * </p>
  */
    @Test
    void isSignalLevelChanged() {
     assertFalse(operativePart.isSignalLevelChanged());
    }
 /**
  * <p> Test de la fonction setSignalLevelChanged.
  * </p>
  */
    @Test
    void setSignalLevelChanged() {
     operativePart.setSignalLevelChanged(true);
     assertTrue(operativePart.isSignalLevelChanged());
    }
 /**
  * <p> Test de la fonction isGoingUp.
  * </p>
  */
    @Test
    void isGoingUp() {
     assertFalse(operativePart.isGoingUp());
    }
 /**
  * <p> Test de la fonction setGoingUp.
  * </p>
  */
 @Test
 void setGoingUp() {
  operativePart.setGoingUp(true);
  assertTrue(operativePart.isGoingUp());
 }

 /**
  * <p> Test de la fonction isGoingDown
  * </p>
  */
    @Test
    void isGoingDown() {
        operativePart.setGoingDown(true);
        assertTrue(operativePart.isGoingDown());
    }
 /**
  * <p> Test de la fonction isSignalUpLimit
  * </p>
  */
    @Test
    void isSignalUpLimit() {
     assertFalse(operativePart.isSignalUpLimit());

    }
 /**
  * <p> Test de la fonction isSignalDownLimit
  * </p>
  */
    @Test
    void isSignalDownLimit() {
     assertFalse(operativePart.isSignalUpLimit());
    }
 /**
  * <p> Test de la fonction getCurrentHeightAsSteps
  * </p>
  */
    @Test
    void getCurrentHeightAsSteps() {
     assertEquals(0,operativePart.getCurrentHeightAsSteps());
    }
 /**
  * <p> Test de la fonction isEmergencyStopped
  * </p>
  */
 @Test
 void isEmergencyStopped() {
  assertFalse(operativePart.isEmergencyStopped());
 }
 /**
  * <p> Test de la fonction isGoingDownStopsNext
  * </p>
  */
 @Test
 void isGoingDownStopsNext() {
  assertFalse(operativePart.isGoingDownStopsNext());
 }
 /**
  * <p> Test de la fonction setGoingDownStopsNext
  * </p>
  */
 @Test
 void setGoingDownStopsNext() {
  operativePart.setGoingDownStopsNext(true);
  assertTrue(operativePart.isGoingDownStopsNext());
 }
 /**
  * <p> Test de la fonction isGoingUpStopsNext
  * </p>
  */
 @Test
 void istGoingUpStopsNext() {
  assertFalse(operativePart.isGoingUpStopsNext());
 }
 /**
  * <p> Test de la fonction setGoingUpStopsNext
  * </p>
  */
 @Test
 void setGoingUpStopsNext() {
  operativePart.setGoingUpStopsNext(true);
  assertTrue(operativePart.isGoingUpStopsNext());
 }
 /**
  * <p> Test de la fonction isStopped
  * </p>
  */
 @Test
 void isStopped() {
  assertTrue(operativePart.isStopped());
 }
 /**
  * <p> Test de la fonction getNumberOfSteps
  * </p>
  */
 @Test
 void getNumberOfSteps() {
  assertEquals(1,operativePart.getNumberOfSteps());
 }

}