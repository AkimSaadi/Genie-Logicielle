package core;


import graphics.GraphicsMotor;
import physics.PhysicalElement;
import physics.PhysicsMotor;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import static physics.Coordinates.doubleEquals;


public class Core {


    private static GraphicsMotor graphicsMotor;
    private static PhysicsMotor  physicsMotor;


    private Scene inGameScene;

    private Scene currentScene;


    public void initializeFirstScene(Scene scene, String fileName, int upperMargin, int lowerMargin) {

        graphicsMotor = new GraphicsMotor(scene.title, fileName , upperMargin , lowerMargin);
        try {System.setErr(new PrintStream("nul"));}catch(FileNotFoundException e){}
        physicsMotor  = new PhysicsMotor();

    }


    public void loadScene (Scene scene) {

        if (currentScene != null)
            currentScene.disable();

        scene.enable();

        currentScene = scene;
        graphicsMotor.setSceneContent(scene.sceneContent);

        graphicsMotor.repaint();

    }


    public void gameLoop() {

        while (true) {


            for (SceneElement element : currentScene.sceneElements) {

                element.graphicalElement.tryToRefreshAnimation();

                if (
                        !element.isEnabled ||
                                System.currentTimeMillis() - element.getLastRefreshed() < element.getMillisecondsPerRefresh()
                )
                    continue;


                element.setLastRefreshed(System.currentTimeMillis());


                if (element.isInteractive() && element.getKeyboard().checkKeysUpdatedFlag())
                    element.manageKeyboard();


                if (element.physicalElement != null) {


                    if (!doubleEquals(element.physicalElement.getSpeed(), 0)) {

                        ArrayList <PhysicalElement> collidedWithPhysicalElements = element.physicalElement.tryToMove();

                        ArrayList <Object> collidedWithElements = findInSceneFromPhysical(collidedWithPhysicalElements);



                        if (collidedWithElements.size() > 0)
                            element.getInputOutputElement().runCollisionManager(collidedWithElements);

                    }


                }

                if (element.graphicalElement.isAnimatedImage())
                    element.graphicalElement.getAnimatedImage().setRefreshAnimationFlag(true);

                element.refreshElementIfNeeded();


            }


            for (SceneElement element : currentScene.getPhysicalElementsList()) {

                if (element.graphicalElement != null && element.physicalElement.checkMovedFlag()) {

                    element.graphicalElement.setLocation(new Point((int) element.physicalElement.getCoordinates().getX(), (int) element.physicalElement.getCoordinates().getY()));

                    element.graphicalElement.repaint();

                }

            }


        }

    }


    private ArrayList<Object> findInSceneFromPhysical(ArrayList<PhysicalElement> collidedWithPhysicalElements) {

        ArrayList <Object> list = new ArrayList<>();

        for (PhysicalElement physical : collidedWithPhysicalElements) {
            list.add(currentScene.getSceneElementsFromPhysicalKeys().get(physical));
        }

        return list;

    }


    public int getHeight() {
        return graphicsMotor.getHeight();
    }

    public int getWidth() {
        return graphicsMotor.getWidth();
    }

    public Scene getCurrentScene() { return currentScene; }

    public Scene getInGameScene() {
        return inGameScene;
    }

    public void setInGameScene(Scene pacManLevelInitialized) {
        inGameScene = pacManLevelInitialized;
    }

}