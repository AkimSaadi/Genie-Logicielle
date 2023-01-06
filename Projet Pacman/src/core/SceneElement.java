package core;

import graphics.GraphicalElement;

import input.EventManager;
import input.InputOutputElement;
import input.Keyboard;
import physics.PhysicalElement;


// a rendre abstract
// (et donc les murs il faudra une classe et rajouter les fantomes)

public abstract class SceneElement {


    protected GraphicalElement graphicalElement = new GraphicalElement();
    protected PhysicalElement physicalElement = null;
    protected InputOutputElement inputOutputElement = null;

    protected boolean isEnabled = true;

    protected boolean isInCurrentScene = false;

    private long lastRefreshed = 0;
    private long millisecondsPerRefresh;


    private PhysicalElement collided;


    public SceneElement() {
    }

    public SceneElement(PhysicalElement physicalElement) {
        this.physicalElement = physicalElement;
    }

    public SceneElement(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }


    public SceneElement(PhysicalElement physicalElement, boolean isEnabled) {
        this.physicalElement = physicalElement;
        this.isEnabled = isEnabled;
    }



    public void manageKeyboard () {
        inputOutputElement.runKeyboardManager();
    }






    public GraphicalElement getGraphicalElement() {
        return graphicalElement;
    }

    public PhysicalElement getPhysicalElement() {
        return physicalElement;
    }

    public boolean isEnabled() {
        return isEnabled && isInCurrentScene;
    }


    public boolean isInteractive () {

        if (inputOutputElement != null)
            return inputOutputElement.getKeyboard() != null;

        return false;

    }


    public void setInCurrentScene (boolean isInCurrentScene) {
        this.isInCurrentScene = isInCurrentScene;

        if (isInCurrentScene) {
            if (isEnabled) {
                graphicalElement.setEnabled(true);
                if (physicalElement != null)
                    physicalElement.enable();
            }
        }
        else {
            graphicalElement.setEnabled(false);

            if (physicalElement != null)
                physicalElement.disable();
        }
    }


    public Keyboard getKeyboard() { return inputOutputElement.getKeyboard(); }

    public void changeSpeed(double movesPerSecond) {

        if (movesPerSecond != 0)
            millisecondsPerRefresh = (long) (1000.0 / movesPerSecond);
        else
            millisecondsPerRefresh = 0;

        this.physicalElement.setSpeed(movesPerSecond);

    }

    public void enable() {

        graphicalElement.setEnabled(true);
        physicalElement.enable();

        isEnabled = true;

    }

    public void disable() {

        graphicalElement.setEnabled(false);

        if (physicalElement != null)
            physicalElement.disable();

        isEnabled = false;

    }

    public long getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(long lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }

    public void setPhysicalElement(PhysicalElement physicalElement) {
        this.physicalElement = physicalElement;
    }

    public void setCollisionManager (EventManager collisionManager) {

        if (inputOutputElement != null)
            inputOutputElement.setCollisionManager(collisionManager);

        if (physicalElement != null) {
            if (collisionManager == null)
                physicalElement.setReactsOnCollision(false);
            else
                physicalElement.setReactsOnCollision(true);
        }

    }


    public void setKeyboardManager (EventManager keyboardManager) {

        if (inputOutputElement != null)
            inputOutputElement.setKeyboardManager(keyboardManager);

    }


    public long getMillisecondsPerRefresh() {
        return millisecondsPerRefresh;
    }

    public void refreshElementIfNeeded() {
        // méthode à Override dans votre élément héritant de SceneElement

    }



    public EventManager getCollisionManager() {

        if (inputOutputElement == null)
            return null;

        return inputOutputElement.getCollisionManager();

    }


    public void setInputOutputElement(InputOutputElement inputOutputElement) {
        this.inputOutputElement = inputOutputElement;
    }

    public InputOutputElement getInputOutputElement() {
        return inputOutputElement;
    }

}