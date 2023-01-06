package input;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Keyboard {


    private KeyListener listener;


    private  boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private boolean startPressed = false;


    private boolean keysUpdatedFlag = false;


    private  int leftKey  = 37;
    private  int rightKey = 39;
    private  int upKey    = 38;
    private  int downKey  = 40;

    private  int startKey = 10;


    public Keyboard () {
        listener = new MyKeyListener();
    }

    public Keyboard(int[] keys) {

        setAllKeys(keys);
        listener = new MyKeyListener();

    }

    public int[] getDefaultKeys() {
        return new int[]{leftKey, rightKey, upKey, downKey, startKey};
    }


    public void setLeftKey(int leftKey) {
        this.leftKey = leftKey;
    }

    public void setRightKey(int rightKey) {
        this.rightKey = rightKey;
    }

    public void setUpKey(int upKey) {
        this.upKey = upKey;
    }

    public void setDownKey(int downKey) {
        this.downKey = downKey;
    }

    public void setStartKey(int startKey) {
        this.startKey = startKey;
    }

    public void setAllKeys(int[] allKeys) {

        assert(allKeys.length == 5);

        setLeftKey(allKeys[0]);
        setRightKey(allKeys[1]);
        setUpKey(allKeys[2]);
        setDownKey(allKeys[3]);

        setStartKey(allKeys[4]);

    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isStartPressed() {
        return startPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }


    public boolean checkKeysUpdatedFlag() {

        boolean flag = keysUpdatedFlag;

        keysUpdatedFlag = false;

        return flag;

    }

    public void setStartPressed(boolean b) {
        startPressed=b;
    }


    private class MyKeyListener implements KeyListener {



        // Un bouton a été pressé
        @Override
        public void keyPressed (KeyEvent e) {

            keysUpdatedFlag = true;

            if (e.getKeyCode() == upKey) {
                upPressed = true;
                return;
            }

            if (e.getKeyCode() == downKey) {
                downPressed = true;
                return;
            }

            if (e.getKeyCode() == leftKey) {
                leftPressed = true;
                return;
            }

            if (e.getKeyCode() == rightKey) {
                rightPressed = true;
                return;
            }

            if (e.getKeyCode() == startKey) {
                startPressed = true;
                return;
            }


        }


        // Un bouton a été relaché
        @Override
        public void keyReleased (KeyEvent e) {

            keysUpdatedFlag = true;

            if (e.getKeyCode() == upKey) {
                upPressed = false;
                return;
            }

            if (e.getKeyCode() == downKey) {
                downPressed = false;
                return;
            }

            if (e.getKeyCode() == leftKey) {
                leftPressed = false;
                return;
            }

            if (e.getKeyCode() == rightKey) {
                rightPressed = false;
                return;
            }

            if (e.getKeyCode() == startKey) {
                startPressed = false;
                return;
            }

        }


        // Un bouton unicode (0-9 A-z) a été pressé/relaché
        @Override
        public void keyTyped (KeyEvent e) {
            // Nous devons l'implémenter, mais nous n'en avons pas besoin, donc on met un corps vide
        }

    }

    public KeyListener getKeyListener() {
        return listener;
    }

}