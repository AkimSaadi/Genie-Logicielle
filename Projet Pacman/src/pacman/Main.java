package pacman;

import core.*;


public class Main {


    public static void main(String[] args) {


        Core core = new Core();

        core.setInGameScene(new Scene("Pacman Niveau 1"));

        Scene testCollisionScene = new Scene("DEMO PACMAN EQUIPE 24");

        core.initializeFirstScene(testCollisionScene, "assets/pacmanLogo.png", 100,50);

        PacmanLevels.pacManTitleScreenMenu(core);

        core.gameLoop();
    }


}