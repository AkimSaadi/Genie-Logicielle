package pacman;

import core.*;

import pacman.characters.CharacterElement;
import pacman.characters.GhostCharacter;
import pacman.characters.PacmanCharacter;
import pacman.fruits.*;
import physics.*;

import graphics.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import static graphics.GraphicsMotor.lowerMargin;
import static graphics.GraphicsMotor.upperMargin;


public class PacmanLevels {


    private static boolean[][] originalMatrixCollider = PacmanFileReader.levelMatrixFromFile("assets/pacmanLabyrintheMatrice.txt");

    private static boolean[][] originalMatrixPacGum = PacmanFileReader.gumMatrixFromFile("assets/pacgumMatriceInitialisation.txt");

    private static boolean[][] matrixCollider;

    private static boolean[][] matrixPacGum;


    public static final int baseSize = 16;

    public static final int sizeY = 32;

    public static final int sizeX = 30;

    public static final int startOfY = -baseSize / 2 + upperMargin;

    public static final int endOfX = (sizeX - 1) * baseSize;

    public static final int endOfY = startOfY + sizeY * baseSize;

    public static final int startOfX = -baseSize / 2;

    public static int currentNumberLevel = 1;

    public static String textScorePlayerOne = "0" ;

    public static String textScorePlayerTwo ;


    private static UIElement scoreElementPlayerOne = new UIElement();

    private static UIElement scoreElementPlayerTwo = new UIElement();

    private static UIElement highScoreElement = new UIElement();


    private static  UIElement timeElement = new UIElement();

    private static  UIElement lifePlayOne = new UIElement();

    private static  UIElement lifePlayTwo = new UIElement();



    private static ArrayList<ArrayList<SceneElement>> wallsMatrix = new ArrayList();
    private static int highScore;



    private static int timeWidth;

    private static boolean hasTwoPlayer = false;

    public static long startTimeOfLevel;

    public static void pacManLevelInitialized(Core core) {

        startTimeOfLevel = System.currentTimeMillis();
        matrixCollider = originalMatrixCollider.clone();
        matrixPacGum   = originalMatrixPacGum.clone();


        Scene level = core.getInGameScene();

        setHighScore();
        PacGumElement.setPacGumsAvailable(238);
        timeWidth = (int)(core.getWidth()*0.3333);
        if(!hasTwoPlayer) {
            timeWidth = core.getWidth() / 2;
            scoreElementPlayerOne.getGraphicalElement().makeText("go !", new Point(0, 56), core.getWidth(), 40, false, 255, 20, level.getSceneContent());
            lifePlayOne.getGraphicalElement().makeText("Life 3", new Point(0,endOfY+1),timeWidth,30,false,255,20,level.getSceneContent());
        }
        highScoreElement.getGraphicalElement().makeText("niveau " + currentNumberLevel, new Point(0, 6), core.getWidth(), 40, false, 255, 20, level.getSceneContent());
        timeElement.getGraphicalElement().makeText("time :\n"+120,new Point(timeWidth,endOfY+1),timeWidth,30,false,255,20,level.getSceneContent());

        level.addElement(highScoreElement);
        level.addElement(timeElement);
        level.addElement(lifePlayOne);


        PacmanCharacter pacmanYellow = new PacmanCharacter(startOfX + 14 * baseSize-16, endOfY - 9 * baseSize, true, true, core);

        switch (currentNumberLevel) {

            case 1:
                pacmanYellow.changeSpeed((double) baseSize * 6 * 2.000);
                pacmanYellow.setCurrentScore(0, level.sceneContent);

                CherryElement cherry = new CherryElement(startOfX, startOfY, baseSize);
                level.addElement(cherry);

                level.setBackground(Color.blue);
                break;

            case 2:
                pacmanYellow.changeSpeed((double) baseSize * 6 * 1.750);

                StrawberryElement strawberry = new StrawberryElement(startOfX, startOfY, baseSize);
                level.addElement(strawberry);

                level.setBackground(new Color(194, 64, 64));
                break;

            case 3:
                pacmanYellow.changeSpeed((double) baseSize * 6 * 1.500);

                OrangeElement orange = new OrangeElement(startOfX, startOfY, baseSize);
                level.addElement(orange);

                level.setBackground(new Color(214, 144, 39));
                break;

            case 4:
                pacmanYellow.changeSpeed((double) baseSize * 6 * 1.250);

                AppleElement apple = new AppleElement(startOfX, startOfY, baseSize);
                level.addElement(apple);

                level.setBackground(new Color(137, 96, 179));
                break;

            case 5:
                pacmanYellow.changeSpeed((double) baseSize * 6 * 0.750);

                MelonElement melon = new MelonElement(startOfX, startOfY, baseSize);
                level.addElement(melon);

                level.setBackground(new Color(130, 189, 98));
                break;

        }


        UIElement upperMarginElement = new UIElement();
        UIElement lowerMarginElement = new UIElement();


        upperMarginElement.getGraphicalElement().makeRectangle(new Point(0,0), core.getWidth(), upperMargin, level.getBackground());
        lowerMarginElement.getGraphicalElement().makeRectangle(new Point(0, endOfY - baseSize/2),core.getWidth(), lowerMargin, level.getBackground());



        ShovelElement shovel = new ShovelElement(new Coordinates(startOfX + 14 * baseSize, startOfY + 5 * baseSize), baseSize);





        //pacmanYellow.makeRectangle(new Point((int) pacmanYellowPhysical.getBox().getCoordinates().getX(),(int)pacmanYellowPhysical.getBox().getCoordinates().getY()),(int) pacmanYellowPhysical.getBox().getWidth(), (int) pacmanYellowPhysical.getBox().getHeight(), Color.red);
        //pacmanYellow.makeRectangle(new Point((int) pacmanYellowAnimation.getX(), (int) pacmanYellowAnimation.getY()), (int) pacmanYellowAnimation.getWidth(), (int) pacmanYellowAnimation.getHeight(), Color.YELLOW);


        level.addElement(pacmanYellow);


        if(hasTwoPlayer) {


            lifePlayTwo.getGraphicalElement().makeText("P2: 3", new Point((int)(core.getWidth()*0.666666),endOfY+1),timeWidth,30,false,255,20,level.getSceneContent());
            scoreElementPlayerOne.getGraphicalElement().makeText("go !", new Point(0, 56), core.getWidth()/2, 40, false, 255, 20, level.getSceneContent());
            scoreElementPlayerTwo.getGraphicalElement().makeText("go !", new Point(core.getWidth()/2, 56), core.getWidth()/2, 40, false, 255, 20, level.getSceneContent());
            lifePlayOne.getGraphicalElement().makeText("P1: 3", new Point(0,endOfY+1),timeWidth,30,false,255,20,level.getSceneContent());



            // z=90 q=81 s=83 d=68 espace=32
            PacmanCharacter pacmanBlue = new PacmanCharacter(startOfX + 14 * baseSize-10+10, endOfY - 9 * baseSize, true, false, new int[]{81, 68, 90, 83, 32},core);
            switch (currentNumberLevel) {

                case 1:
                    pacmanBlue.changeSpeed((double) baseSize * 6 * 2.000);
                    pacmanBlue.setCurrentScore(0, level.sceneContent);
                    break;

                case 2:
                    pacmanBlue.changeSpeed((double) baseSize * 6 * 1.750);
                    break;

                case 3:
                    pacmanBlue.changeSpeed((double) baseSize * 6 * 1.500);
                    break;

                case 4:
                    pacmanBlue.changeSpeed((double) baseSize * 6 * 1.250);
                    break;

                case 5:
                    pacmanBlue.changeSpeed((double) baseSize * 6 * 0.750);
                    break;

            }

            level.addElement(pacmanBlue);
            level.addElement(scoreElementPlayerTwo);
            level.addElement(lifePlayOne);
            level.addElement(lifePlayTwo);

        }



        level.addElement(scoreElementPlayerOne);


        level.addElement(upperMarginElement);
        level.addElement(lowerMarginElement);




       for (int i = 0; i < 32; i++) {

            wallsMatrix.add(new ArrayList<SceneElement>());

            for (int j = 0; j < 29; j++) {

                if (matrixCollider[i][j]) {


                    WallElement wall = new WallElement(new PhysicalElement(new Coordinates(startOfX + j * baseSize, startOfY + i * baseSize)));
                    wall.getPhysicalElement().setBox(new CollisionBox(new Coordinates(startOfX + j * baseSize - baseSize / 2, startOfY + i * baseSize - baseSize / 2), baseSize * 2, baseSize * 2, true));


                    //wall.getGraphicalElement().makeRectangle(new Point(startOfX + j * baseSize - baseSize/2, startOfY + i * baseSize - baseSize/2), (int) physical.getBox().getWidth(), (int) physical.getBox().getHeight(), Color.RED);
                    wall.getGraphicalElement().makeRectangle((new Point((int) wall.getPhysicalElement().getCoordinates().getX(), (int) wall.getPhysicalElement().getCoordinates().getY())), (int) wall.getPhysicalElement().getBox().getWidth() / 2, (int) wall.getPhysicalElement().getBox().getHeight() / 2, Color.BLACK);

                    wall.getGraphicalElement().setVisibleWhenEnabled(false);

                    level.addElement(wall);


                    // pour effacer physiquement et graphiquement les murs
                    // wall.disable(); wall.getGraphicalElement().setCanBeVisible(false);


                    wallsMatrix.get(i).add(wall);
                } else {
                    wallsMatrix.get(i).add(null);
                }

            }

        }


        BackGroundElement level_sprite = new BackGroundElement();
        FixedImage sprite = new FixedImage(0, upperMargin, 448, 496, "assets/spriteTables/levelSprite.png");
        level_sprite.getGraphicalElement().makeImage(sprite);


        level.addElement(shovel);


        //level.addElement(pacmanBlue);


        for (int i = 0; i < 32; i++) {

            for (int j = 0; j < 29; j++) {

                if (matrixPacGum[i][j]) {

                    FixedImage pacGumImage = new FixedImage(startOfX + j * baseSize, startOfY + i * baseSize, 16, 16, "assets/spriteTables/bonus/pacgum.png");

                    pacGumImage.scale(2);

                    PacGumElement pacGum = new PacGumElement(new PhysicalElement(new Coordinates(startOfX + j * baseSize, startOfY + i * baseSize)));
                    pacGum.getPhysicalElement().setBox(new CollisionBox(pacGum.getPhysicalElement().getCoordinates().nextCoordinates(new Vector(baseSize / 2, baseSize / 2)), baseSize, baseSize, false));

                    pacGum.getGraphicalElement().makeImage(pacGumImage);

                    level.addElement(pacGum);
                }


            }

        }


        SuperGumElement superGumTopLeft = new SuperGumElement(new Coordinates(startOfX + 1 * baseSize, startOfY + 3 * baseSize), baseSize, core);
        level.addElement(superGumTopLeft);

        SuperGumElement superGumBottomLeft = new SuperGumElement(new Coordinates(startOfX + 1 * baseSize, startOfY + 23 * baseSize), baseSize, core);
        level.addElement(superGumBottomLeft);

        SuperGumElement superGumTopRight = new SuperGumElement(new Coordinates(startOfX + 26 * baseSize, startOfY + 3 * baseSize), baseSize, core);
        level.addElement(superGumTopRight);

        SuperGumElement superGumBottomRight = new SuperGumElement(new Coordinates(startOfX + 26 * baseSize, startOfY + 23 * baseSize), baseSize, core);
        level.addElement(superGumBottomRight);


        level.addElement(level_sprite); // a add en dernier

        core.loadScene(level);

    }


    public static void pacManTitleScreenMenu(Core core) {

        Scene menu = new Scene("Ecran D'Accueil");

        int charactersScale = 4;

        int ghostPaintPerSecond = 6;

        int pacManPaintPerSecond = 16;


        menu.setBackground(Color.darkGray);
        FixedImage titleImage = new FixedImage((core.getWidth() - 356) / 2, 75, 356, 82, "assets/pacmanTitle.png");


        AnimatedImage blueGhostImage = new AnimatedImage(0, 0, 16, 16, ghostPaintPerSecond, "assets/spriteTables/ghosts/blueRight.png");
        AnimatedImage pinkGhostImage = new AnimatedImage(0, 0, 16, 16, ghostPaintPerSecond, "assets/spriteTables/ghosts/pinkRight.png");
        AnimatedImage orangeGhostImage = new AnimatedImage(0, 0, 16, 16, ghostPaintPerSecond, "assets/spriteTables/ghosts/orangeRight.png");
        AnimatedImage redGhostImage = new AnimatedImage(0, 0, 16, 16, ghostPaintPerSecond, "assets/spriteTables/ghosts/redRight.png");



        blueGhostImage.scale(charactersScale);
        pinkGhostImage.scale(charactersScale);
        orangeGhostImage.scale(charactersScale);
        redGhostImage.scale(charactersScale);


        int margin = (
                (
                        core.getWidth() -

                                (blueGhostImage.getWidth()
                                        + pinkGhostImage.getWidth()
                                        + orangeGhostImage.getWidth()
                                        + redGhostImage.getWidth()
                                        + 16 * charactersScale
                                )

                ) / 3

        );


        blueGhostImage.setPosition(margin + 0 * (core.getWidth() - margin * 2) / 4 - blueGhostImage.getWidth() / 2, (int) (core.getHeight() * 0.6 - blueGhostImage.getHeight() * 0.5));

        pinkGhostImage.setPosition(margin + 1 * (core.getWidth() - margin * 2) / 4 - pinkGhostImage.getWidth() / 2, (int) (core.getHeight() * 0.6 - pinkGhostImage.getHeight() * 0.5));

        orangeGhostImage.setPosition(margin + 2 * (core.getWidth() - margin * 2) / 4 - orangeGhostImage.getWidth() / 2, (int) (core.getHeight() * 0.6 - orangeGhostImage.getHeight() * 0.5));

        redGhostImage.setPosition(margin + 3 * (core.getWidth() - margin * 2) / 4 - redGhostImage.getWidth() / 2, (int) (core.getHeight() * 0.6 - redGhostImage.getHeight() * 0.5));


        GhostCharacter blueGhost = new GhostCharacter();
        blueGhost.getGraphicalElement().makeImage(blueGhostImage);

        GhostCharacter pinkGhost = new GhostCharacter();
        pinkGhost.getGraphicalElement().makeImage(pinkGhostImage);

        GhostCharacter orangeGhost = new GhostCharacter();
        orangeGhost.getGraphicalElement().makeImage(orangeGhostImage);

        GhostCharacter redGhost = new GhostCharacter();
        redGhost.getGraphicalElement().makeImage(redGhostImage);

        /*
        PacmanCharacter pacManCharacter = new PacmanCharacter(margin + 4 * (core.getWidth() - margin * 2) / 4 - 16*charactersScale / 2, (int) (core.getHeight() * 0.6 - 16*charactersScale * 0.5), false, true, core);
        pacManCharacter.getGraphicalElement().getAnimatedImage().scale(charactersScale);*/


        AnimatedImage pacManCharacterImage = new AnimatedImage(0, 0, 16, 16, pacManPaintPerSecond, "assets/spriteTables/pacman/pacmanYellow.png");
        pacManCharacterImage.scale(charactersScale);
        pacManCharacterImage.setPosition(margin + 4 * (core.getWidth() - margin * 2) / 4 - 16*charactersScale / 2, (int) (core.getHeight() * 0.6 - 16*charactersScale * 0.5));
        CharacterElement pacManCharacter = new CharacterElement();
        pacManCharacter.getGraphicalElement().makeImage(pacManCharacterImage);


        UIElement playButtonUI = new UIElement();
        playButtonUI.getGraphicalElement().makeButton("Jouer", margin, (int) (core.getHeight() * 0.75), (int) (core.getWidth() / 3), 40);

        UIElement optionButtonUI = new UIElement();
        optionButtonUI.getGraphicalElement().makeButton("Options", (int) (core.getWidth()) - margin - (int) (core.getWidth() / 3), (int) (core.getHeight() * 0.75), (int) (core.getWidth() / 3), 40);


        playButtonUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameModeChoice(core);
                    }
                }
        );

        optionButtonUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        option(core,false);
                    }
                }
        );

        UIElement teammateNames = new UIElement();
        teammateNames.getGraphicalElement().makeText("Equipe 24 - PLAS - IKHOU - SAADI - KHELIFI AHMED", new Point(core.getWidth() / 2 - 175, core.getHeight() - 50), 350, 30, true);


        UIElement title = new UIElement();
        title.getGraphicalElement().makeImage(titleImage);



        menu.addElement(title);

        menu.addElement(playButtonUI);
        menu.addElement(optionButtonUI);

        menu.addElement(teammateNames);

        menu.addElement(blueGhost);
        menu.addElement(pinkGhost);
        menu.addElement(orangeGhost);
        menu.addElement(redGhost);

        menu.addElement(pacManCharacter);


        core.loadScene(menu);
    }

    private static void setHighScore() {
        try {
            BufferedReader fluxEntree = new BufferedReader(new FileReader("assets/highScore.txt"));
            highScore = Integer.parseInt(fluxEntree.readLine());
            fluxEntree.close();
        }
        catch(IOException exc){
            exc.printStackTrace();
        }

    }

    public static void setHighScore (int highScore) {
        PacmanLevels.highScore = highScore;
    }

    public static void saveHighScore (){
        try {
            BufferedReader fluxEntree = new BufferedReader(new FileReader("assets/highScore.txt"));
            int currentHighScore = Integer.parseInt(fluxEntree.readLine());
            fluxEntree.close();
            if (highScore > currentHighScore) {
                Writer fluxSortie = new PrintWriter(new BufferedWriter(new FileWriter("assets/highScore.txt")));
                fluxSortie.write(String.valueOf(highScore));
                fluxSortie.close();
            }
        }catch(IOException exc){
            exc.printStackTrace();
        }
    }

    public static ArrayList<ArrayList<SceneElement>> getWallsMatrix() {
        return wallsMatrix;
    }

    public static void setMatrixCollider(boolean[][] newMatrix) {
        matrixCollider = newMatrix;
    }

    public static boolean[][] getMatrixCollider() {
        return matrixCollider;
    }

    public static SceneElement getScoreElementPlayerOne() {
        return scoreElementPlayerOne;
    }

    public static SceneElement getScoreElementPlayerTwo() {

        return scoreElementPlayerTwo;
    }

    public static void gameModeChoice (Core core) {

        Scene choice = new Scene("Choix de mode de jeu");

        int charactersScale = 4;

        int pacManPaintPerSecond = 10;

        choice.setBackground(Color.darkGray);


        AnimatedImage pacManCharacterImage = new AnimatedImage(0, 0, 16, 16, pacManPaintPerSecond, "assets/spriteTables/pacman/pacmanYellow.png");
        AnimatedImage twoPacManCharacterImage = new AnimatedImage(0, 0, 32, 16, pacManPaintPerSecond, "assets/spriteTables/pacman/Two_Pac_Mac.png");


        pacManCharacterImage.scale(charactersScale);
        twoPacManCharacterImage.scale(charactersScale);





        pacManCharacterImage.setPosition((int)(core.getWidth()*0.333)-50, endOfY/3+10);
        twoPacManCharacterImage.setPosition((int)(core.getWidth()*0.6)-24,endOfY/3+10);

        CharacterElement pacManCharacter = new CharacterElement();
        pacManCharacter.getGraphicalElement().makeImage(pacManCharacterImage);

        CharacterElement twoPacManCharacter = new CharacterElement();
        twoPacManCharacter.getGraphicalElement().makeImage(twoPacManCharacterImage);


        UIElement onePlayerButtonUI = new UIElement();
        onePlayerButtonUI.getGraphicalElement().makeButton("Un joueur", (int)(core.getWidth()*0.333)-65, (endOfY/3)+78, 100,40);


        UIElement twoPlayerButtonUI = new UIElement();
        twoPlayerButtonUI.getGraphicalElement().makeButton("Deux joueurs", (int)(core.getWidth()*0.6)-30, (endOfY/3)+78, 150,40);


        UIElement teammateNames = new UIElement();
        teammateNames.getGraphicalElement().makeText("Equipe 24 - PLAS - IKHOU - SAADI - KHELIFI AHMED", new Point(core.getWidth() / 2 - 175, core.getHeight() - 50), 350, 30, true);

        UIElement title = new UIElement();
        title.getGraphicalElement().makeText("Choix du mode de jeu", new Point(core.getWidth() / 2 - 175,  50), 350, 200, true);

        UIElement backMenuButtonUI = new UIElement();
        backMenuButtonUI.getGraphicalElement().makeButton("Retour au menu", (int)(core.getWidth()*0.5)-75, (endOfY/3)+160, 150,40);

        onePlayerButtonUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hasTwoPlayer = false;
                        pacManLevelInitialized(core);
                    }
                }
        );

        twoPlayerButtonUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hasTwoPlayer = true;
                        pacManLevelInitialized(core);
                    }
                }
        );

        backMenuButtonUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pacManTitleScreenMenu(core);
                    }
                }
        );

        choice.addElement(onePlayerButtonUI);
        choice.addElement(twoPlayerButtonUI);
        choice.addElement(backMenuButtonUI);

        choice.addElement(teammateNames);
        choice.addElement(title);

        choice.addElement(pacManCharacter);
        choice.addElement(twoPacManCharacter);


        core.loadScene(choice);
    }

    public static void option(Core core, boolean fromPauseMenu){

        Scene option  = new Scene("Option");
        option.setBackground(Color.white);

        UIElement rulesButtonUI = new UIElement();
        rulesButtonUI.getGraphicalElement().makeButton("Regle du jeu", (int)(core.getWidth()*0.333)-65, (endOfY/3)+78, 150,40);
        rulesButtonUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        rules(core, fromPauseMenu);
                    }
                }
        );

        UIElement controllerButtonUI = new UIElement();
        controllerButtonUI.getGraphicalElement().makeButton("Commande", (int)(core.getWidth()*0.6)-30, (endOfY/3)+78, 150,40);
        controllerButtonUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller(core, fromPauseMenu);
                    }
                }
        );

        UIElement backMenuButtonUI = new UIElement();
        backMenuButtonUI.getGraphicalElement().makeButton("Retour au menu", (int)(core.getWidth()*0.333)-65, (endOfY/3)+160, 150,40);

        backMenuButtonUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentNumberLevel=1;
                        core.setInGameScene(new Scene("Pacman Niveau "+ currentNumberLevel));
                        pacManTitleScreenMenu(core);
                    }
                }
        );

        UIElement creditButtonUI = new UIElement();
        creditButtonUI.getGraphicalElement().makeButton("Credit", (int)(core.getWidth()*0.6)-30, (endOfY/3)+160, 150,40);

        creditButtonUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        credit(core, fromPauseMenu);
                    }
                }
        );


        option.addElement(rulesButtonUI);
        option.addElement(controllerButtonUI);
        option.addElement(backMenuButtonUI);
        option.addElement(creditButtonUI);

        if (fromPauseMenu) {
            UIElement backGame = new UIElement();
            backGame.getGraphicalElement().makeButton("Retour au jeu", (int) (core.getWidth() * 0.6) - 30, (endOfY / 3) + 240, 150, 40);

            backGame.getGraphicalElement().addActionListener(

                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            core.loadScene(core.getInGameScene());
                        }
                    }
            );
            option.addElement(backGame);
        }

        core.loadScene(option);

    }

    private static void credit(Core core, boolean fromPauseMenu) {
        Scene credit  = new Scene("Credit");
        UIElement backOptionUI = new UIElement();
        backOptionUI.getGraphicalElement().makeButton("Retour a option", (int)(core.getWidth()*0.3)-75, (int)(core.getHeight()*0.5)-20, 150,40);
        credit.setBackground(Color.white);
        backOptionUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        option(core,fromPauseMenu);
                    }
                }
        );
        credit.addElement(backOptionUI);

        UIElement rulesText1 = new UIElement();
        rulesText1.getGraphicalElement().makeText("Ce jeu de Pacman a ete code par Akim",new Point(0,0),core.getWidth(),20);
        UIElement rulesText2 = new UIElement();
        rulesText2.getGraphicalElement().makeText(" SAADI, Zakari Ikhou et jean pierre",new Point(0,20),core.getWidth(),20);
        UIElement rulesText3 = new UIElement();
        rulesText3.getGraphicalElement().makeText(" plas",new Point(0,40),core.getWidth(),20);
        credit.addElement(rulesText1);
        credit.addElement(rulesText2);
        credit.addElement(rulesText3);
        if (fromPauseMenu) {
            UIElement backGame = new UIElement();
            backGame.getGraphicalElement().makeButton("Retour au jeu", (int)(core.getWidth()*0.66666)-75, (int)(core.getHeight()*0.5)-20, 150, 40);

            backGame.getGraphicalElement().addActionListener(

                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            core.loadScene(core.getInGameScene());
                        }
                    }
            );
            credit.addElement(backGame);
        }
        core.loadScene(credit);
    }

    public static void rules(Core core, boolean fromPauseMenu) {
        Scene rules  = new Scene("Rules");
        rules.setBackground(Color.white);



        UIElement backOptionUI = new UIElement();
        backOptionUI.getGraphicalElement().makeButton("Retour a option", (int)(core.getWidth()*0.3)-75, (int)(core.getHeight()*0.5)-20, 150,40);

        backOptionUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        option(core,fromPauseMenu);
                    }
                }
        );

        UIElement rulesText1 = new UIElement();
        rulesText1.getGraphicalElement().makeText("Pour le mode un joueur le but est de manger",new Point(0,0),core.getWidth(),20);

        UIElement rulesText2 = new UIElement();
        rulesText2.getGraphicalElement().makeText("tous les pacgums en moins de 120 secondes,",new Point(0,20),core.getWidth()-10,20);
        UIElement rulesText3 = new UIElement();
        rulesText3.getGraphicalElement().makeText(" plus le niveau monte moins la vitesse" ,new Point(0,40),core.getWidth()-65,20);
        UIElement rulesText4 = new UIElement();
        rulesText4.getGraphicalElement().makeText("est elevee",new Point(0,60),(int)(core.getWidth()*0.28),20);

        UIElement rulesText5 = new UIElement();
        rulesText5.getGraphicalElement().makeText("Pour le mode deux joueurs le but est le",new Point(0,100),core.getWidth(),20);
        UIElement rulesText6 = new UIElement();
        rulesText6.getGraphicalElement().makeText( "meme si ce n'est que le but est d'avoir un ",new Point(0,120),core.getWidth(),20);
        UIElement rulesText7 = new UIElement();
        rulesText7.getGraphicalElement().makeText("meilleur score que l'autre et avec le ",new Point(0,140),core.getWidth(),20);
        UIElement rulesText8 = new UIElement();
        rulesText8.getGraphicalElement().makeText(" pouvoir du super packgum on peut tuer",new Point(0,160),core.getWidth(),20);
        UIElement rulesText9 = new UIElement();
        rulesText9.getGraphicalElement().makeText("l'adversaire",new Point(0,180),core.getWidth(),20);
        rules.addElement(rulesText1);
        rules.addElement(rulesText2);
        rules.addElement(rulesText3);
        rules.addElement(rulesText4);
        rules.addElement(rulesText5);
        rules.addElement(rulesText6);
        rules.addElement(rulesText7);
        rules.addElement(rulesText8);
        rules.addElement(rulesText9);
        rules.addElement(backOptionUI);

        if (fromPauseMenu) {
            UIElement backGame = new UIElement();
            backGame.getGraphicalElement().makeButton("Retour au jeu", (int)(core.getWidth()*0.66666)-75, (int)(core.getHeight()*0.5)-20, 150, 40);

            backGame.getGraphicalElement().addActionListener(

                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            core.loadScene(core.getInGameScene());
                        }
                    }
            );
            rules.addElement(backGame);
        }

        core.loadScene(rules);
    }

    private static void controller(Core core, boolean fromPauseMenu) {
        Scene controller  = new Scene("Controller");


        controller.setBackground(Color.white);
        UIElement backOptionUI = new UIElement();
        backOptionUI.getGraphicalElement().makeButton("Retour a option", (int)(core.getWidth()*0.33)-75, (int)(core.getHeight()*0.5)-20, 150,40);

        backOptionUI.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        option(core,fromPauseMenu);
                    }
                }
        );

        UIElement rulesText1 = new UIElement();
        rulesText1.getGraphicalElement().makeText("Pour le joueur 1, les fleches directionnels ",new Point(0,20),core.getWidth(),20);
        UIElement rulesText3 = new UIElement();
        rulesText3.getGraphicalElement().makeText("pour se deplacer et entre pour mettre pause",new Point(0,40),core.getWidth(),20);

        UIElement rulesText2 = new UIElement();
        rulesText2.getGraphicalElement().makeText("Pour le joueur 2, ZQSD pour se deplacer et ",new Point(0,60),core.getWidth(),20);
        UIElement rulesText4 = new UIElement();
        rulesText4.getGraphicalElement().makeText("espace pour mettre pause",new Point(0,80),core.getWidth(),20);

        controller.addElement(rulesText1);
        controller.addElement(rulesText2);
        controller.addElement(rulesText3);
        controller.addElement(rulesText4);
        controller.addElement(backOptionUI);

        if (fromPauseMenu) {
            UIElement backGame = new UIElement();
            backGame.getGraphicalElement().makeButton("Retour au jeu", (int)(core.getWidth()*0.66666)-75, (int)(core.getHeight()*0.5)-20, 150, 40);

            backGame.getGraphicalElement().addActionListener(

                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            core.loadScene(core.getInGameScene());
                        }
                    }
            );
            controller.addElement(backGame);
        }

        core.loadScene(controller);
    }

    public static SceneElement getHighScoreElement() {
        return highScoreElement;
    }

    public static int getHighScore() {
        return highScore;
    }

    public static boolean isHasTwoPlayer() {
        return hasTwoPlayer;
    }

    public static void congratulations(Core core) {

        Scene congratulations  = new Scene("congratulations");
        congratulations.setBackground(Color.white);
        UIElement congratulationsText = new UIElement();
        if (!hasTwoPlayer) {
            congratulationsText.getGraphicalElement().makeText("Felicitation vous avez fini les 5 niveaux", new Point(0, 0), core.getWidth(), core.getHeight() / 3);
            UIElement congratulationsText2 = new UIElement();
            congratulationsText2.getGraphicalElement().makeText("a temps", new Point(0, 20), core.getWidth(), core.getHeight() / 3);
            congratulations.addElement(congratulationsText2);
        }
        else {
            congratulationsText.getGraphicalElement().makeText("Felicitation vous avez fini les 5 niveaux", new Point(0, 0), core.getWidth(), 20);
            UIElement congratulationsText2 = new UIElement();
            congratulationsText2.getGraphicalElement().makeText("Et en plus battu l'autre pack man", new Point(0, 40), core.getWidth(), 20);
            congratulations.addElement(congratulationsText2);
            UIElement congratulationsText3 = new UIElement();
            congratulationsText3.getGraphicalElement().makeText("a temps", new Point(0, 20), core.getWidth(), core.getHeight() / 3);
            congratulations.addElement(congratulationsText3);
        }

        congratulations.addElement(congratulationsText);

        UIElement backMenu = new UIElement();
        backMenu.getGraphicalElement().makeButton("Retour au menu", core.getWidth()/2-75, (endOfY/3)+78, 150,40);

        backMenu.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentNumberLevel=1;
                        core.setInGameScene(new Scene("Pacman Niveau "+ currentNumberLevel));
                        pacManTitleScreenMenu(core);
                    }
                }
        );
        congratulations.addElement(backMenu);

        core.loadScene(congratulations);
    }

    public static void gameOver (Core core){
        Scene gameOver  = new Scene("Game Over");
        UIElement congratulationsText = new UIElement();
        congratulationsText.getGraphicalElement().makeText("Game Over !",new Point(0,0), core.getWidth(),core.getHeight()/2, true);
        gameOver.addElement(congratulationsText);
        gameOver.setBackground(Color.BLACK);

        UIElement backMenu = new UIElement();
        backMenu.getGraphicalElement().makeButton("Retour au menu", core.getWidth()/2-75, (endOfY/3)+78, 150,40);

        backMenu.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentNumberLevel=1;
                        core.setInGameScene(new Scene("Pacman Niveau "+ currentNumberLevel));
                        pacManTitleScreenMenu(core);
                    }
                }
        );
        gameOver.addElement(backMenu);

        core.loadScene(gameOver);
    }

    public static UIElement getTimeElement() {
        return timeElement;
    }
    public static int getTimeWidth() {
        return timeWidth;
    }

    public static UIElement getLifePlayOne() {
        return lifePlayOne;
    }

    public static UIElement getLifePlayTwo() {
        return lifePlayTwo;
    }

    public static void pauseMenu(Core core) {
        Scene pauseMenu  = new Scene("Pause");
        UIElement backGameButton = new UIElement();
        backGameButton.getGraphicalElement().makeButton("Retour au jeu", core.getWidth()/3-75, (endOfY/3)+78, 150,40);
        pauseMenu.addElement(backGameButton);
        pauseMenu.setBackground(Color.BLACK);


        backGameButton.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        core.loadScene(core.getInGameScene());
                    }
                }
        );
        UIElement backMenuButton = new UIElement();
        backMenuButton.getGraphicalElement().makeButton("Retour au menu", (int)(core.getWidth()*(0.66666)-75), (endOfY/3)+78, 150,40);

        backMenuButton.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentNumberLevel=1;
                        core.setInGameScene(new Scene("Pacman Niveau "+ currentNumberLevel));
                        pacManTitleScreenMenu(core);
                    }
                }
        );
        pauseMenu.addElement(backMenuButton);

        UIElement optionButton = new UIElement();
        optionButton.getGraphicalElement().makeButton("Option", (int)(core.getWidth()*(0.66666)-75), (endOfY/3)+78, 150,40);

        optionButton.getGraphicalElement().addActionListener(

                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        option(core,true);
                    }
                }
        );
        pauseMenu.addElement(optionButton);
        pauseMenu.addElement(backMenuButton);


        UIElement pauseText = new UIElement();
        pauseText.getGraphicalElement().makeText("Pause ",new Point(0,0), core.getWidth(),core.getHeight()/2, true);
        pauseMenu.addElement(pauseText);

        core.loadScene(pauseMenu);

    }
}