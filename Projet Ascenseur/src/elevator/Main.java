package elevator;

public class Main {
    /**
     * <p> Le main...
     * </p>
     *
     * @param args Arguments of main()
     *
     */

    public static void main(String[] args) {

        final int numberOfLevels = 6;

        final int numberOfSteps = 1;


        Graphical_User_Interface GUI = new Graphical_User_Interface(numberOfLevels, numberOfSteps);

        GUI.setVisible(true);
    }
}
