package core;

import graphics.SceneContent;

import input.InputOutputElement;
import physics.PhysicalElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Scene {


    public    SceneContent sceneContent = new SceneContent();

    protected ArrayList <SceneElement> sceneElements          = new ArrayList<>();
    protected ArrayList <SceneElement> physicalElementsList   = new ArrayList<>();



    protected HashMap <PhysicalElement, SceneElement> sceneElementsFromPhysicalKeys = new HashMap <PhysicalElement, SceneElement>();

    protected String title;

    protected boolean isEnabled = false;


    public Scene(String title) {
        this.title = title;
    }


    protected boolean isEnabled() {
        return isEnabled;
    }

    public void enable () {
        this.isEnabled = true;

        for (SceneElement element : sceneElements) {
            element.setInCurrentScene(true);
        }

        sceneContent.enable();
    }

    public void disable () {
        this.isEnabled = false;

        for (SceneElement element : sceneElements) {
            element.setInCurrentScene(false);
        }

        sceneContent.disable();
    }

    public String getTitle() {
        return title;
    }

    public ArrayList getSceneElements() {
        return sceneElements;
    }


    public ArrayList<SceneElement> getPhysicalElementsList() {
        return physicalElementsList;
    }


    public void addElement (SceneElement element) {

        if (element.graphicalElement != null)
            sceneContent.add(element.graphicalElement);

        if (element.physicalElement != null) {
            physicalElementsList.add(element);
            sceneElementsFromPhysicalKeys.put(element.physicalElement, element);
        }

        if (element.inputOutputElement != null)
            if (element.inputOutputElement.getKeyboard() != null)
                addKeyListener(element.inputOutputElement);

        if (element instanceof SceneElement)
            sceneElements.add(element);


    }


    public HashMap <PhysicalElement, SceneElement> getSceneElementsFromPhysicalKeys() {
        return sceneElementsFromPhysicalKeys;
    }

    public SceneContent getSceneContent() {
        return sceneContent;
    }

    public void setBackground (Color background) {
        sceneContent.setBackground(background);
    }

    public void addKeyListener(InputOutputElement inputOutputElement) {
        sceneContent.addKeyListener(inputOutputElement.getKeyboard().getKeyListener());
    }

    public Color getBackground() {
        return sceneContent.getBackground();
    }
}
