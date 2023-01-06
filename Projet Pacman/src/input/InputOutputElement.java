package input;


import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputOutputElement {


    private Keyboard keyboard = null;

    private EventManager keyboardManager = null;

    private EventManager collisionManager = new EventManager();


    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard (Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public void setKeyboardManager (EventManager keyboardManager) {
        this.keyboardManager = keyboardManager;
    }


    public void setCollisionManager(EventManager collisionManager) {
        this.collisionManager = collisionManager;
    }


    public void runKeyboardManager() {

        if (keyboardManager != null) {
            keyboardManager.run();
        }

    }

    public void runCollisionManager(ArrayList <Object> collidedWith) {

        if (collisionManager != null) {

            collisionManager.setArguments(collidedWith);
            collisionManager.run();

        }

    }

    public EventManager getCollisionManager() {
        return collisionManager;
    }

    public KeyListener getKeyListener() {
        return keyboard.getKeyListener();
    }
}