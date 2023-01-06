package elevator;

public class Operative_Part {
    private final double elevatorSizeHeight;
    private final int numberOfSteps;

    private boolean goingUp = false;
    private boolean goingDown = false;
    private boolean goingUpStopsNext = false;
    private boolean goingDownStopsNext = false;

    private boolean stopped = true;
    private boolean emergencyStopped = false;

    private boolean signalUpLimit = false;
    private boolean signalDownLimit = false;

    private double elevatorPosition = 0;

    private int currentHeightAsSteps = 0;
    private int stepsToCurrentDestination = 0;

    private boolean signalLevelChanged = false;


    /**
     * <p> Constructeur de Command_Control.
     * </p>
     *
     * @see Operative_Part
     */

    public Operative_Part(int numberOfSteps, double elevatorSizeHeight) {
        this.numberOfSteps = numberOfSteps;
        this.elevatorSizeHeight = elevatorSizeHeight;
    }


    /**
     * <p> Met a jour d'une etape et gere les changements qui se sont passe.
     * </p>
     */

    public void refreshStep() {

        if (emergencyStopped)
            return;

        if (goingUp || goingUpStopsNext)
            currentHeightAsSteps++;

        if (goingDown || goingDownStopsNext)
            currentHeightAsSteps--;

        //System.out.println("montant ? " + montant);
        elevatorPosition = currentHeightAsSteps * elevatorSizeHeight / numberOfSteps;

        /*
        System.out.println("currentHeightAsSteps = " + currentHeightAsSteps);
        System.out.println("elevatorPosition = " + elevatorPosition);
        System.out.println("elevatorSizeHeight = " + elevatorSizeHeight);
        */

        if (!stopped && currentHeightAsSteps % numberOfSteps == 0)
            signalLevelChanged();

        /*
        System.out.println("signalLevelChanged = " + signalLevelChanged);
        System.out.println("goingDownStopsNext = " + goingDownStopsNext);
        System.out.println("goingUpStopsNext = " + goingUpStopsNext);
        */

        if (signalLevelChanged && (goingDownStopsNext || goingUpStopsNext)) {
            goingDownStopsNext = false;
            goingUpStopsNext = false;

            stopped = true;
            System.out.println("stopped = " + stopped);
        }


    }


    /**
     * <p> Lance le signal signalLevelChanged.
     * </p>
     */

    public void signalLevelChanged() {
        signalLevelChanged = true;
    }


    /**
     * <p> Lance le signal signalUpLimit.
     * </p>
     */

    public void signalUpLimit() {
        if (goingDown || goingDownStopsNext) {
            //System.out.println("signalUpLimit() emergencyStop();");
            emergencyStop();
        }

        signalUpLimit = true;
    }

    /**
     * <p> Lance le signal signalDownLimit.
     * </p>
     */

    public void signalDownLimit() {
        if (goingUp || goingUpStopsNext) {
            //System.out.println("signalDownLimit() emergencyStop();");

            emergencyStop();
        }

        signalDownLimit = true;
    }

    /**
     * <p> Passe à l'etat montant goingUp.
     * </p>
     */

    public void goUp() {
        if (emergencyStopped)
            return;

        //System.out.println("monter()");
        if (goingDown || goingDownStopsNext) {
            //System.out.println("goUp() emergencyStop();");

            emergencyStop();
        }


        if (!goingUpStopsNext)
            goingUp = true;

        stopped = false;
    }


    /**
     * <p> Passe à l'etat descendant goingDown.
     * </p>
     */

    public void goDown() {
        if (emergencyStopped)
            return;

        /*
        System.out.println("goDown()");
        System.out.println("goingUp = " + goingUp);
        System.out.println("goingUpStopsNext = " + goingUpStopsNext);
        */

        if (goingUp || goingUpStopsNext) {
            //System.out.println("goDown() emergencyStop();");

            emergencyStop();
        }
        if (!goingDownStopsNext)
            goingDown = true;
        stopped = false;
    }


    /**
     * <p> Passe soit:
     * - de l'etat descendant a descendant puis arret goingDownStopsNext
     * - de l'etat montant a montant puis arret goingUpStopsNext
     * </p>
     */

    public void stopNextLevel() {

        if (emergencyStopped || goingUpStopsNext || goingDownStopsNext)
            return;

        //System.out.println("stopNextLevel();");


        if (goingUp) {

            goingUpStopsNext = true;
        } else if (goingDown) {

            goingDownStopsNext = true;
        } else {
            //System.out.println("stopNextLevel() emergencyStop();");

            emergencyStop();
        }


        goingUp = false;
        goingDown = false;
    }


    /**
     * <p> Passe à l'etat d'arret d'urgence emergencyStopped.
     * </p>
     */

    public void emergencyStop() {
        goingUp = false;
        goingUpStopsNext = false;
        goingDown = false;
        goingDownStopsNext = false;
        stopped = false;

        signalDownLimit = false;
        signalUpLimit = false;

        emergencyStopped = true;

        //System.out.println("emergencyStopped = " + emergencyStopped);
    }


    /**
     * <p> Change le nombre d'etapes a franchir avant d'arriver a la prochaine destination.
     * </p>
     */

    public void setStepsToCurrentDestination(int currentDestination) {
        if (emergencyStopped)
            return;

        stepsToCurrentDestination = currentDestination * numberOfSteps;
    }

    /**
     * <p> Getter de stepsToCurrentDestination.
     * </p>
     *
     * @return nombre d'etapes a franchir avant d'arriver a la prochaine destination.
     */

    public int getStepsToCurrentDestination() {
        return stepsToCurrentDestination;
    }
    /**
     * <p> Getter de elevatorPosition.
     * </p>
     *
     * @return la position de l'ascenseur dans la fenetre.
     */

    public double getElevatorPosition() {
        return elevatorPosition;
    }


    /**
     * <p> Getter de signalLevelChanged.
     * </p>
     *
     * @return l'etat du signal boolean signalLevelChanged.
     */

    public boolean isSignalLevelChanged() {
        return signalLevelChanged;
    }


    /**
     * <p> Setter de signalLevelChanged.
     * </p>
     */

    public void setSignalLevelChanged(boolean signalLevelChanged) {
        this.signalLevelChanged = signalLevelChanged;
    }
    /**
     * <p> Getter de goingUp.
     * </p>
     *
     * @return l'etat du signal boolean goingUp.
     */
    public boolean isGoingUp (){return goingUp;}
    /**
     * <p> Setter de goingUp.
     * </p>
     */
    public void setGoingUp (boolean bool){ goingUp = bool;}
    /**
     * <p> Getter de goingDown.
     * </p>
     *
     * @return l'etat du signal boolean goingDown.
     */
    public boolean isGoingDown (){return goingDown;}
    /**
     * <p> Setter de goingDown.
     * </p>
     */
    public void setGoingDown (boolean bool){ goingDown = bool;}
    /**
     * <p> Getter de signalUpLimit.
     * </p>
     *
     * @return l'etat du signal boolean signalUpLimit.
     */
    public boolean isSignalUpLimit (){return signalUpLimit;}
    /**
     * <p> Getter de signalDownLimit.
     * </p>
     *
     * @return l'etat du signal boolean signalDownLimit.
     */
    public boolean isSignalDownLimit (){return signalDownLimit;}
    /**
     * <p> Getter de emergencyStopped.
     * </p>
     *
     * @return l'etat du signal boolean emergencyStopped.
     */
    public boolean isEmergencyStopped (){return emergencyStopped;}
    /**
     * <p> Getter de currentHeightAsSteps.
     * </p>
     *
     * @return la hauteur de l'etape actuelle
     */
    public int getCurrentHeightAsSteps(){return currentHeightAsSteps;}
    /**
     * <p> Setter de currentHeightAsSteps.
     * </p>
     */
    public void setCurrentHeightAsSteps(int heightAsSteps ){currentHeightAsSteps = heightAsSteps;}
    /**
     * <p> Getter de goingDownStopsNext
     * </p>
     *
     * @return l'etat du boolean goingDownStopNext.
     */
    public boolean isGoingDownStopsNext (){return goingDownStopsNext;}
    /**
     * <p> Setter de goingDownStopsNext
     * </p>
     */
    public void setGoingDownStopsNext (boolean bool){goingDownStopsNext = bool;}
    /**
     * <p> Getter de goingUpStopsNext
     * </p>
     * @return l'etat du boolean goingUpStopNext.
     */
    public boolean isGoingUpStopsNext (){return goingUpStopsNext;}
    /**
     * <p> Setter de goingUpStopsNext
     * </p>
     */
    public void setGoingUpStopsNext (boolean bool){goingUpStopsNext = bool;}

    /**
     * <p> Getter de stopped
     * </p>
     * @return l'etat du boolean stopped.
     */
    public boolean isStopped (){return stopped;}
    /**
     * <p> Getter de numberOfSteps
     * </p>
     * @return le nombre d'etape
     */
    public int getNumberOfSteps (){return numberOfSteps;}
    /**
     * <p> Setter de stopped
     * </p>
     */
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

}
