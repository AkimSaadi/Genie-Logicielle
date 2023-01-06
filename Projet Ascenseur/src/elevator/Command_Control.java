package elevator;

import java.util.ArrayList;


public class Command_Control {

    private int numberOfLevels; // + 2 car il faut au moins 2 niveaux sinon l'ascenseur ne sert à rien

    private int currentDestination = 0;

    private int currentLevel = 0;

    private ArrayList<Integer> nextDestinations = new ArrayList<>();

    private Operative_Part operative_part;


    /**
     * <p> Constructeur de Command_Control.
     * </p>
     *
     * @see Command_Control
     */

    public Command_Control(int numberOfLevels, Operative_Part operative_part) {
        this.numberOfLevels = numberOfLevels;
        this.operative_part = operative_part;
    }


    /**
     * <p> Ajoute une destination a la liste des prochaines destinations nextDestinations.
     * </p>
     */


    /**
     * <p> Ajoute une destination a la liste des prochaines destinations nextDestinations.
     * </p>
     */


    public void addDestination(int level) {

        if (!(nextDestinations.contains(level) || currentDestination == level)) {
            // le niveau n'est pas dans la liste et ce n'est pas la destination actuelle
            // on verifie la direction est-ce qu'on monte, descend ou on est a l'arret


            int index = 0;


            System.out.println("\n");


            if (operative_part.isGoingUp() || operative_part.isGoingUpStopsNext()) {
                // on est MONTANT
                // on verifie ou on est par rapport au niveau actuel


                if (level > currentLevel) {
                    // on monte et que le niveau est superieur au niveau actuel
                    // on vérifie ou on est par rapport a la destination actuelle


                    if (level < currentDestination) {
                        // on monte et le niveau est entre le niveau actuel et la destination actuelle
                        // il faut qu'on change la destination actuelle et on remet l'ancienne dans la liste a l'emplacement 0


                        // exemple:

                        // on veut rajouter la destination (5)      niveau actuel = 4    destination actuelle = 6     [ ]


                        nextDestinations.add(0, currentDestination); // remet l'ancienne destination dans la liste
                        currentDestination = level; // change la destination actuelle
                        //System.out.println("ICI 1");

                    } else {
                        // on monte et le niveau est superieur a la destination actuelle
                        // Il faut qu'on parte de 0 tant que niveau superieur a get(i) on avance. On le pose a (i ==) index


                        // exemple:

                        // on veut rajouter la destination (6)  niveau actuel = 3    destination actuelle = 4     [5 7  2 1]


                        for (int i = 0; i < nextDestinations.size() && level > nextDestinations.get(i); i++)
                            index++;

                        nextDestinations.add(index, level);
                        //System.out.println("ICI 2");

                    }

                } else {
                    // on monte et le niveau est inferieur au niveau actuel
                    // il faut qu'on part de size et tant que niveau superieur a get(i) on avance. on le pose a (i+1 ==) index
                    //                                        et tant que >= 0


                    // exemple:

                    // on veut rajouter la destination (3)      niveau actuel = 4    destination actuelle = 5     [ 6    2 1  ]


                    index = nextDestinations.size();

                    for (int i = index - 1; i >= 0 && level > nextDestinations.get(i); i--)
                        index--;

                    nextDestinations.add(index, level);
                    //System.out.println("ICI 3");

                }
            } else if (operative_part.isGoingDown() || operative_part.isGoingDownStopsNext()) {
                // on est DESCENDANT
                // on verifie la ou on est par rapport au niveau actuel


                if (level < currentLevel) {
                    // on descend et le niveau est inferieur au niveau actuel
                    // on verifie la ou on est par rapport a la destination actuelle


                    if (level > currentDestination) {
                        // on descend et le niveau est entre: [le niveau actuel et la destination actuelle]
                        // il faut qu'on change la destination actuelle et on remet l'ancienne dans l'index 0 de la liste


                        // exemple:

                        // on veut rajouter la destination (4)      niveau actuel = 5    destination actuelle = 3     [ 2 1   6 7 ]


                        nextDestinations.add(0, currentDestination);
                        currentDestination = level;
                        //System.out.println("ICI 4");
                    } else {
                        // on descend et le niveau est inferieur a la destination actuelle
                        // Il faut qu'on parte de 0 tant que niveau < get(i) et on le pose a (i ==) index


                        // exemple:

                        // on veut rajouter la destination (2)      niveau actuel = 7    destination actuelle = 4     [ 3 1   8 9 ]


                        for (int i = 0; i < nextDestinations.size() && level < nextDestinations.get(i); i++)
                            index++;

                        nextDestinations.add(index, level);
                        //System.out.println("ICI 5");
                    }


                } else {
                    // on descend et le niveau est superieur au niveau actuel
                    // il faut qu'on parte de size tant que niveau inferieur a get(i) et on le pose a (i + 1 ==) index


                    // exemple:

                    // on veut rajouter la destination (7)      niveau actuel = 4         [ 3 2     6 8 ]


                    index = nextDestinations.size();

                    for (int i = index - 1; i >= 0 && level < nextDestinations.get(i); i--)
                        index--;

                    nextDestinations.add(index, level);
                    //System.out.println("ICI 6");


                }
            } else if (!operative_part.isEmergencyStopped()) {
                // on est a l'état d'arrêt
                // il faut qu'on ajoute directement le niveau a l'index 0


                // exemple:

                // on veut rajouter la destination (4)      niveau actuel = 3     isStopped == true    [   ]


                nextDestinations.add(0, level);
                //System.out.println("ICI  7");

            }


            refreshStep();

            System.out.println(nextDestinations);

        }
    }
            /**
             * <p> Met a jour la destination ou on se dirige actuellement currentDestination.
             * </p>
             */

    public void refreshCurrentDestination() {

        if (currentLevel != currentDestination)
            return;

        currentDestination = nextDestinations.remove(0);
        operative_part.setStepsToCurrentDestination(currentDestination);

    }


    /**
     * <p> Met a jour la niveau ou on se trouve actuellement currentLevel.
     * </p>
     */

    public void refreshCurrentLevel() {
        if (currentDestination > currentLevel)
            currentLevel++;
        else
            currentLevel--;
    }

    /**
     * <p> Met a jour d'une etape et gere les changements qui se sont passe.
     * </p>
     */

    public void refreshStep() {

        if (operative_part.isSignalLevelChanged()) {
            refreshCurrentLevel();
            operative_part.setSignalLevelChanged(false);
        }


        if (!nextDestinations.isEmpty()) {

            refreshCurrentDestination();


            if (currentLevel < currentDestination)
                operative_part.goUp();
            else if (currentLevel > currentDestination)
                operative_part.goDown();
        }

        /*
        System.out.println("Niveau actuel : " + currentLevel);
        System.out.println("Destination actuelle : " + currentDestination);
        System.out.println("Distance avec destination : " + Math.abs(currentLevel - currentDestination));
        */


        if (Math.abs(currentLevel - currentDestination) == 1)
            operative_part.stopNextLevel();


        if (currentDestination == 0)
            operative_part.signalDownLimit();


        if (currentDestination == numberOfLevels - 1)
            operative_part.signalUpLimit();

    }

    /**
     * <p> Ordonne un arret d'urgence.
     * </p>
     *
     */

    public void emergencyStop() {
        operative_part.emergencyStop();
    }

    /**
     * <p> Getter de la liste des prochaines destinations nextDestinations.
     * </p>
     *
     */
    public ArrayList<Integer> getNextDestinations() {
        return nextDestinations;
    }

    /**
     * <p> Getter de la variable contenant la destination actuelle currentDestination.
     * </p>
     *
     */
    public int getCurrentDestination(){
        return currentDestination;
    }

    /**
     * <p> Setter de la variable contenant la destination actuelle de l'ascenseur currentDestination.
     * </p>
     *
     */
    public void setCurrentDestination(int level){
        currentDestination=level;
    }

    /**
     * <p> Getter de la variable contenant  l'étage auquel se situe l'ascenseu CurrentLevel.
     * </p>
     *
     */
    public int getCurrentLevel(){
        return currentLevel;
    }
    /**
     * <p> Setter de la variable contenant  l'étage auquel se situe l'ascenseu CurrentLevel.
     * </p>
     *
     */
    public void setCurrentLevel(int level){
        currentLevel=level;
    }

    /**
     * <p> Getter de la partie operative operative_part.
     * </p>
     *
     */
    public Operative_Part getOperative_part(){
        return operative_part;
    }


}