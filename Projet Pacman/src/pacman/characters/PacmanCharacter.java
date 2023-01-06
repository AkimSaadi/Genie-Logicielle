package pacman.characters;

import core.Core;
import core.SceneElement;

import graphics.AnimatedImage;
import graphics.SceneContent;
import input.EventManager;
import input.InputOutputElement;
import input.Keyboard;

import pacman.*;

import physics.CollisionBox;
import physics.Coordinates;
import physics.PhysicalElement;
import physics.Vector;

import java.awt.*;
import java.util.ArrayList;

import static graphics.GraphicsMotor.upperMargin;
import static pacman.GamePlay.tryToChangeLevel;
import static pacman.GamePlay.updateTime;
import static pacman.PacmanLevels.*;


public class PacmanCharacter extends CharacterElement {

    private static final int paintPerSecond = 16;

    

    private int currentScore = 0;

    private int numberOfLives = 3;

    

    private boolean superPacPower = false;

    private boolean hasDigPower = false;

    private boolean isPlayerOne = true;

    private boolean isInvincible = false;

    private  long startTimeOfInvincible;

    private long startTimeSuperPacPower;


    private Core core;

    public PacmanCharacter(int x, int y, boolean isPhysical, boolean isPlayerOne, Core core) {
        super();

        this.core = core;

        makePacman(x, y, isPhysical, isPlayerOne, new Keyboard().getDefaultKeys());
    }

    public PacmanCharacter(int x, int y, boolean isPhysical, boolean isPlayerOne, int[] keys, Core core) {
        super();

        this.core = core;

        makePacman(x, y, isPhysical, isPlayerOne, keys);

    }

    private void makePacman (int x, int y, boolean isPhysical, boolean isPlayerOne, int[] keys) {

        this.isPlayerOne = isPlayerOne;
        AnimatedImage animation = new AnimatedImage(x, y,16, 16, paintPerSecond, isPlayerOne ? "assets/spriteTables/pacman/pacmanYellow.png" : "assets/spriteTables/pacman/pacmanBlue.png");
        animation.scale((baseSize*2) / 16);

        getGraphicalElement().makeImage(animation);

        if (isPhysical) {
            PhysicalElement physical = new PhysicalElement(new Coordinates(animation.getX(), animation.getY()));
            physical.setBox(new CollisionBox(physical.getCoordinates().nextCoordinates(new Vector(baseSize / 2, baseSize / 2)), baseSize, baseSize, true));

            setPhysicalElement(physical);
        }


        changeSpeed(1);


        setInputOutputElement(new InputOutputElement());

        inputOutputElement.setKeyboard(new Keyboard(keys));

        setCollisionManager(makeCollisionManager());
        setKeyboardManager(makeKeyboardManager());

        if (isPlayerOne) {
            if (textScorePlayerOne != null)
                currentScore = Integer.parseInt(textScorePlayerOne);
        }
        else {
            if (textScorePlayerTwo != null)
                currentScore = Integer.parseInt(textScorePlayerTwo);
        }

    }

    public boolean isWallInDirection() {


        Coordinates coordinatesInMatrix = toMatrixCoordinates();


        int xInMatrix = (int) coordinatesInMatrix.getX();
        int yInMatrix = (int) coordinatesInMatrix.getY();


        if (getWantedDirection() == NO_DIRECTION) {
            return false;
        }


        /*

        Xp
        pp
        MM

        (y+2, x) (y+2, x+1)

        */

        // On veut aller vers le bas
        else if (getWantedDirection() == DIRECTION_DOWN) {

            if (wouldCollideWithWall(new Coordinates(xInMatrix + 0, yInMatrix + 2))) return true;
            if (wouldCollideWithWall(new Coordinates(xInMatrix + 1, yInMatrix + 2))) return true;
            if (wouldCollideWithWall(new Coordinates(xInMatrix + 2, yInMatrix + 2))) return true;

        }

        /*

        MM
        P


        (y-1, x) (y-1, x+1)

        */

        else if (getWantedDirection() == DIRECTION_UP) {

            if (wouldCollideWithWall(new Coordinates(xInMatrix + 0, yInMatrix - 1))) return true;
            if (wouldCollideWithWall(new Coordinates(xInMatrix + 1, yInMatrix - 1))) return true;
            if (wouldCollideWithWall(new Coordinates(xInMatrix + 2, yInMatrix - 1))) return true;

        }

        /*

        P M
          M

        (y, x+2) (y+1, x+2)

        */

        else if (getWantedDirection() == DIRECTION_RIGHT) {

            if (wouldCollideWithWall(new Coordinates(xInMatrix + 2, yInMatrix + 0))) return true;
            if (wouldCollideWithWall(new Coordinates(xInMatrix + 2, yInMatrix + 1))) return true;
            if (wouldCollideWithWall(new Coordinates(xInMatrix + 2, yInMatrix + 2))) return true;

        }

        /*

         xP
        MPP
        M
        (y, x-1) (y+1, x-1)

        */

        else if (getWantedDirection() == DIRECTION_LEFT) {

            if (wouldCollideWithWall(new Coordinates(xInMatrix - 1, yInMatrix + 0)))return true;
            if (wouldCollideWithWall(new Coordinates(xInMatrix - 1, yInMatrix + 1))) return true;
            if (wouldCollideWithWall(new Coordinates(xInMatrix - 1, yInMatrix + 2))) return true;

        }

        return false;
    }


    public boolean wouldCollideWithWall(Coordinates inMatrixCoordinates) {

        SceneElement wall = tryToGetWallInMatrix(inMatrixCoordinates);

        if (wall != null)
            if (wall.isEnabled())
                return physicalElement.wouldCollide(wall.getPhysicalElement(), vectorFromDirection(getWantedDirection()));

        return false;

    }


    public Coordinates toMatrixCoordinates() {

        double x = Math.floor((physicalElement.getCoordinates().getX() + 8) / baseSize);

        double y = Math.floor((physicalElement.getCoordinates().getY() + 8 - upperMargin) / baseSize);

        return new Coordinates(x, y);
    }


    public boolean canChangeDirection() {

        return hasDigPower || !isWallInDirection();

    }


    public void tryToChangeDirection() {

        if (wantedDirection != NO_DIRECTION) {
            if (canChangeDirection()) {

                currentDirection = wantedDirection;
                getPhysicalElement().setVector(vectorFromDirection(wantedDirection));

                if (getWantedDirection() == DIRECTION_RIGHT)
                    getGraphicalElement().rotate(0);

                if (getWantedDirection() == DIRECTION_LEFT)
                    getGraphicalElement().rotate(Math.PI);

                if (getWantedDirection() == DIRECTION_DOWN)
                    getGraphicalElement().rotate(Math.PI / 2);

                if (getWantedDirection() == DIRECTION_UP)
                    getGraphicalElement().rotate(-Math.PI / 2);

            }

        }

    }


    public Vector vectorFromDirection(int wantedDirection) {

        if (getWantedDirection() == DIRECTION_UP) {
            return new Vector(0, -1);
        } else if (getWantedDirection() == DIRECTION_DOWN) {
            return new Vector(0, 1);
        } else if (getWantedDirection() == DIRECTION_LEFT) {
            return new Vector(-1, 0);
        } else if (getWantedDirection() == DIRECTION_RIGHT) {
            return new Vector(1, 0);
        }

        return physicalElement.getVector();

    }


    public Vector vectorFromDirection() {

        if (getWantedDirection() == DIRECTION_UP) {
            return new Vector(0, -1);
        } else if (getWantedDirection() == DIRECTION_DOWN) {
            return new Vector(0, 1);
        } else if (getWantedDirection() == DIRECTION_LEFT) {
            return new Vector(-1, 0);
        } else if (getWantedDirection() == DIRECTION_RIGHT) {
            return new Vector(1, 0);
        }

        return new Vector(0, 0);

    }


    private void tryToDigInDirection(ArrayList<Object> args) {

        if (hasDigPower()) {

            for (Object arg1 : args) {
                SceneElement arg = (SceneElement) arg1;

                if (!(arg instanceof WallElement))
                    continue;

                arg.disable();
            }
        }

    }
    public void updateLifeDisplay ( int livesOfTheOtherPacman){
        if (isPlayerOne) {

            PacmanLevels.getLifePlayOne().getGraphicalElement().makeText("P1:" + numberOfLives, new Point(0, endOfY + 1), getTimeWidth(), 30, false, 255, 20, core.getInGameScene().getSceneContent());
            PacmanLevels.getLifePlayTwo().getGraphicalElement().makeText("P2: " + livesOfTheOtherPacman, new Point((int) (core.getWidth() * 0.666666), endOfY + 1), getTimeWidth(), 30, false, 255, 20, core.getInGameScene().getSceneContent());

        }
        else {
            PacmanLevels.getLifePlayOne().getGraphicalElement().makeText("P1:" + livesOfTheOtherPacman, new Point(0, endOfY + 1), getTimeWidth(), 30, false, 255, 20, core.getInGameScene().getSceneContent());
            PacmanLevels.getLifePlayTwo().getGraphicalElement().makeText("P2: " + numberOfLives, new Point((int) (core.getWidth() * 0.666666), endOfY + 1), getTimeWidth(), 30, false, 255, 20, core.getInGameScene().getSceneContent());
        }
    }
    private static void tryToDigAtMatrixCoordinates(Coordinates inMatrixCoordinates) {

        SceneElement wall = tryToGetWallInMatrix(inMatrixCoordinates);

        if (wall != null) {
            wall.disable();
        }

    }

    private static SceneElement tryToGetWallInMatrix(Coordinates inMatrixCoordinates) {

        SceneElement wall = null;

        if (coordinatesAreInMatrix(inMatrixCoordinates)) {

            ArrayList<SceneElement> row = getWallsMatrix().get((int) inMatrixCoordinates.getY());

            if (row != null)
                wall = row.get((int) inMatrixCoordinates.getX());


        }


        return wall;
    }

    private static boolean coordinatesAreInMatrix(Coordinates inMatrixCoordinates) {

        int X = (int) inMatrixCoordinates.getX();
        int Y = (int) inMatrixCoordinates.getY();

        return 0 <= Y && Y < getWallsMatrix().size()

                &&

                0 <= X && X < getWallsMatrix().get(0).size();

    }

     public void tryToTeleport() {

            if (physicalElement.getCoordinates().getX() < -baseSize)
                physicalElement.setCoordinates(physicalElement.getCoordinates().nextCoordinates(new Vector(endOfX + getPhysicalElement().getBox().getWidth(), 0)));

            if (physicalElement.getCoordinates().getX() > endOfX - baseSize)
                physicalElement.setCoordinates(physicalElement.getCoordinates().nextCoordinates(new Vector(-endOfX - getPhysicalElement().getBox().getWidth(), 0)));


            if (physicalElement.getCoordinates().getY() < startOfY - baseSize*1.5)
                physicalElement.setCoordinates(new Coordinates( physicalElement.getCoordinates().getX(), endOfY - baseSize ));


            if (physicalElement.getCoordinates().getY() > endOfY - baseSize*0.5)
                physicalElement.setCoordinates(new Coordinates( physicalElement.getCoordinates().getX(), startOfY-baseSize  ));


    }


    public void tryToTakeShovel(ArrayList<Object> args) {

        for (Object arg1 : args) {

            SceneElement arg = (SceneElement) arg1;

            if (arg != null) {

                if (arg instanceof ShovelElement && arg.isEnabled()) {

                    arg.disable();
                    setDigPower(true);
                }


            }


        }

    }


    public EventManager makeKeyboardManager() {

        EventManager keyboardManager = new EventManager();

        Runnable manager = () -> {


            PhysicalElement physicalElement = getPhysicalElement();
            Keyboard keyboard = getKeyboard();


            if (keyboard.isUpPressed()) {
                setWantedDirection(DIRECTION_UP);
            } else if (keyboard.isDownPressed()) {
                setWantedDirection(DIRECTION_DOWN);
            } else if (keyboard.isLeftPressed()) {
                setWantedDirection(DIRECTION_LEFT);
            } else if (keyboard.isRightPressed()) {
                setWantedDirection(DIRECTION_RIGHT);
            } else if (keyboard.isStartPressed()) {
                keyboard.setStartPressed(false);
                PacmanLevels.pauseMenu(core);
            }


            if (canChangeDirection()) {

                physicalElement.setVector(vectorFromDirection());

                if (getWantedDirection() == DIRECTION_RIGHT)
                    getGraphicalElement().rotate(0);

                if (getWantedDirection() == DIRECTION_LEFT)
                    getGraphicalElement().rotate(Math.PI);

                if (getWantedDirection() == DIRECTION_DOWN)
                    getGraphicalElement().rotate(Math.PI / 2);

                if (getWantedDirection() == DIRECTION_UP)
                    getGraphicalElement().rotate(-Math.PI / 2);

            }


        };

        keyboardManager.setManager(manager);

        return keyboardManager;

    }


    public void tryToTakePacGum(ArrayList<Object> args) {


        for (Object arg1 : args) {

            SceneElement arg = (SceneElement) arg1;

            if (!(arg instanceof PacGumElement) || !arg.isEnabled())
                continue;

            if (arg != null) {

                arg.disable();


                setCurrentScore(currentScore + ((PacGumElement) arg).getScoreIncrement());
                ((PacGumElement) arg).eatPacGum();
                tryToChangeLevel(core);



            }

        }

    }


    public void tryToTakeSuperPacGum(ArrayList<Object> args) {

        for (Object arg1 : args) {
            SceneElement arg = (SceneElement) arg1;
            if (arg == null)
                continue;

            if (!(arg instanceof SuperGumElement) || !arg.isEnabled())
                continue;

            arg.disable();

            activateSuperPacPower();


        }

    }

    public void tryToTakeBonus(ArrayList<Object> args) {

        for (Object arg1 : args) {

            SceneElement arg = (SceneElement) arg1;

            if (!(arg instanceof BonusElement) || !arg.isEnabled() || arg instanceof ShovelElement || arg instanceof SuperGumElement)
                continue;

            if (arg != null) {

                arg.disable();
                setCurrentScore(currentScore +  ((BonusElement) arg).getScoreIncrement());


            }

        }

    }

    public void doesHeCollideWithPacman(ArrayList<Object> args) {

        for (Object arg1 : args) {

            SceneElement arg = (SceneElement)arg1;

            if (!(arg instanceof PacmanCharacter) || !arg.isEnabled())
                continue;

            if (arg != null && superPacPower && !(((PacmanCharacter) arg).isInvincible())) {

                ((PacmanCharacter) arg).loseLife();
                updateLifeDisplay(((PacmanCharacter) arg).getNumberOfLives());
                if  (((PacmanCharacter) arg).isDead())
                    arg.disable();

            }

        }

    }
    public void doesHeCollideWithGhost(ArrayList<Object> args) {

        for (Object arg1 : args) {
            SceneElement arg = (SceneElement) arg1;


            if (!(arg instanceof GhostCharacter) || !arg.isEnabled())
                continue;

            if (arg != null)
                if (superPacPower)
                    ((GhostCharacter)arg).dead();
                else {
                    if (((GhostCharacter)arg).isAlive())
                    {
                        numberOfLives = 0;
                        disable();
                    }
                }

        }

    }


    public EventManager makeCollisionManager() {


        EventManager collisionManager = new EventManager();

        Runnable manager = () -> {

            ArrayList<Object> args = collisionManager.getArguments();

            tryToTakePacGum(args);
            if (isNextToShovel())
                tryToTakeShovel(args);
            tryToTakeBonus(args);
            if (isNextToSuperPacGum())
                tryToTakeSuperPacGum(args);
            if(PacmanLevels.isHasTwoPlayer())
                doesHeCollideWithPacman(args);
            //doesHeCollideWithGhost(args);
            updateTime(core);

            if (hasDigPower)
                tryToDigInDirection(args);

        };

        collisionManager.setManager(manager);

        return collisionManager;
    }

    private boolean isNextToShovel() {
        if (physicalElement.getCoordinates().getX()> 184 && physicalElement.getCoordinates().getX()< 232)
            if (physicalElement.getCoordinates().getY() == 172)
                return true;
        return false;
    }

    private boolean isNextToSuperPacGum() {
        if ((physicalElement.getCoordinates().getX() < 420 && physicalElement.getCoordinates().getX() >380) ||(physicalElement.getCoordinates().getX() < 25 && physicalElement.getCoordinates().getX() >0) ) {
            if (((physicalElement.getCoordinates().getY() < 200 && physicalElement.getCoordinates().getY() >80)  || (physicalElement.getCoordinates().getY() > 420 && physicalElement.getCoordinates().getY() <500) )) {
                return true;
            }
        }
        return false;
    }


    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int newScore) {
        setCurrentScore(newScore, core.getCurrentScene().getSceneContent());
    }

    public void setCurrentScore (int newScore, SceneContent sceneContent) {

        currentScore = newScore;

        int widthScore = core.getWidth();

        String textScore = "score " ;

        if(PacmanLevels.isHasTwoPlayer()){
            widthScore= widthScore/2;
            textScore = "Yellow ";
            if(!isPlayerOne){
                textScorePlayerTwo = ""+ currentScore;
                PacmanLevels.getScoreElementPlayerTwo().getGraphicalElement().makeText("Blue  "+textScorePlayerTwo, new Point(widthScore, 56), widthScore, 40, false, 255, 20, sceneContent);
            }
        }

        if (isPlayerOne) {

            textScorePlayerOne = "" + currentScore;
            PacmanLevels.getScoreElementPlayerOne().getGraphicalElement().makeText(textScore + textScorePlayerOne, new Point(0, 56), widthScore, 40, false, 255, 20, sceneContent);
        }
        if (currentScore > PacmanLevels.getHighScore()) {
            setHighScore(currentScore);
        }
        PacmanLevels.getHighScoreElement().getGraphicalElement().makeText("highScore " + getHighScore(), new Point(0, 6), core.getWidth(), 40, false, 255, 20, sceneContent);

    }

    public void resetSuperPacPower() {
        superPacPower = false;
    }

    public boolean hasSuperPacPower() {
        return superPacPower;
    }

    public void activateSuperPacPower() {

        superPacPower = true;

        startTimeSuperPacPower = System.currentTimeMillis();

    }

    public boolean hasDigPower() {
        return hasDigPower;
    }

    public void setDigPower(boolean hasDigPower) {
        this.hasDigPower = hasDigPower;
    }

    public boolean isDead() {
        return numberOfLives < 1;
    }

    public void loseLife() {
        numberOfLives -= 1;
        isInvincible = true;
        startTimeOfInvincible = System.currentTimeMillis();
        invincibility();
    }

    private void invincibility() {
        if (System.currentTimeMillis() > Long.sum(startTimeOfInvincible,10000))
            isInvincible = false;

    }

    private void stillHasSuperPacPower() {
        if (System.currentTimeMillis() > Long.sum(startTimeSuperPacPower,15000))
            superPacPower = false;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public boolean isInvincible() {
        return isInvincible;
    }
    @Override
    public void refreshElementIfNeeded() {

        if (physicalElement != null) {
            if (physicalElement.checkMovedFlag())
                physicalElement.setMovedFlag(true);
            else if (graphicalElement != null)
                graphicalElement.getAnimatedImage().setRefreshAnimationFlag(false);

            tryToTeleport();
        }
        if (graphicalElement != null)
            tryToChangeDirection();

        if (isInvincible)
            invincibility();
        if (superPacPower)
            stillHasSuperPacPower();
    }
}