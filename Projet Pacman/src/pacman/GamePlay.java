package pacman;


import core.Core;
import core.Scene;
import physics.PhysicsMotor;

import java.awt.*;

import static pacman.PacGumElement.isAnyAvailable;
import static pacman.PacmanLevels.*;
import static pacman.PacmanLevels.getTimeWidth;

public class GamePlay {

    public static void tryToChangeLevel(Core core) {

        if (!isAnyAvailable()) {

            PacGumElement.resetPacGumsAvailable();

            currentNumberLevel++;
            saveHighScore();

            if (currentNumberLevel == 6) {
                PacmanLevels.congratulations(core);
            }
            else {
                core.setInGameScene(new Scene("Pacman Niveau "+ currentNumberLevel));
                pacManLevelInitialized(core);
            }


        } else if (System.currentTimeMillis() > Long.sum(120000, startTimeOfLevel))
            PacmanLevels.gameOver(core);

    }

    public static void updateTime(Core core) {
        PacmanLevels.getTimeElement().getGraphicalElement().makeText("time:" +(120-((System.currentTimeMillis()-startTimeOfLevel)/ 1000)), new Point(getTimeWidth(), endOfY + 1), getTimeWidth(), 30, false, 255, 20, core.getCurrentScene().getSceneContent());

    }
}
