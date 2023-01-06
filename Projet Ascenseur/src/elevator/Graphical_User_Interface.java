package elevator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Graphical_User_Interface extends JFrame {

    private ArrayList<JButton> insideButtons;
    private ArrayList<JButton> callButtons;


    private JButton defaultButton = new JButton();


    private final double elevatorSizeHeight;


    private final int windowsBarSizeHeight = 42;

    private final int levelSizeHeight = 50;

    private final int elevatorDisplayBottomMargin = 3;

    private final int numberOfLevels;


    private JSlider elevator;


    private int elevatorPosition = 0;


    private boolean stopped = true;


    private Operative_Part operative_part;

    private Command_Control command_control;

    /**
     * <p> Constructeur de l'IHM
     * </p>
     *
     * @param numberOfLevels Le nombre de niveaux auquel peut acceder l'ascenseur.
     * @param numberOfSteps  Le nombre d'etapes que l'on simule entre chaque niveau.
     *
     * @see Graphical_User_Interface
     */

    public Graphical_User_Interface(int numberOfLevels, int numberOfSteps) {
        super("IHM Ascenseur");


        assert (numberOfLevels >= 2);
        assert (numberOfSteps >= 1);


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 2 + windowsBarSizeHeight + levelSizeHeight * numberOfLevels);

        JPanel mainPanel = (JPanel) this.getContentPane();

        mainPanel.setBackground(Color.DARK_GRAY);

        mainPanel.setLayout(new FlowLayout());


        this.numberOfLevels = numberOfLevels;

        JPanel callButtonsPanel = createCallButtons();
        JPanel elevatorPanel = createElevator(callButtonsPanel.getPreferredSize());

        elevatorPanel.setBackground(Color.BLACK);
        mainPanel.add(elevatorPanel);


        mainPanel.add(createCallButtons());


        mainPanel.add(createInsideButtons());
        mainPanel.add(createStepButton());

        elevatorSizeHeight = (52d / (elevatorPanel.getComponent(0).getPreferredSize().height - 14d)) * 100d;


        operative_part = new Operative_Part(numberOfSteps, elevatorSizeHeight);

        command_control = new Command_Control(numberOfLevels, operative_part);

    }

    /*
    private JPanel createLevelIndicators() { // a gauche
        JPanel panel = new JPanel(new GridLayout(numberOfLevels * 2 - 1, 1));

        panel.setBackground(Color.GRAY);


        for (int indexNiveau = numberOfLevels - 1; indexNiveau > 0; indexNiveau--) {

            JButton button = new JButton(Integer.toString(indexNiveau));

            button.setBackground(Color.WHITE);
            button.setEnabled(false);


            panel.add(button);


            panel.add(new JLabel(""));

        }


        JButton button = new JButton("Rc");

        button.setBackground(Color.WHITE);
        button.setEnabled(false);

        panel.add(button);

        return panel;
    }
*/

    /**
     * <p> Cree la representation visuelle de l'ascenseur.
     * </p>
     */

    private JPanel createElevator(Dimension preferredSize) {
        JPanel panel = new JPanel(new GridLayout(1, 1));

        elevator = new JSlider();

        elevator.setBackground(Color.WHITE);

        elevator.setPreferredSize(preferredSize);


        elevator.setValue(elevatorDisplayBottomMargin);
        elevator.setOrientation(SwingConstants.VERTICAL);
        elevator.setEnabled(false);

        panel.add(elevator);

        return panel;
    }


    /*

    private JPanel createCallButtons() {

        JPanel panel = new JPanel();
        JPanel panel0 = new JPanel(new GridLayout(numberOfLevels * 2-1, 2));

        panel.setBackground(Color.WHITE);
        panel0.setBackground(Color.GRAY);


        ArrayList<JButton> buttons = new ArrayList<>();
        ArrayList<ActionListener> actionListeners = new ArrayList<>();



        for (int indexNiveau = numberOfLevels - 1; indexNiveau >= 1; indexNiveau--) {

            actionListeners.add(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    System.out.println("Level: " + ((JButton)(e.getSource())).getName() + " needs to go : " + e.getActionCommand());

                }
            });

            if (indexNiveau == numberOfLevels - 1) {
                JButton button = new JButton("⇓");
                button.setName(Integer.toString(indexNiveau));
                buttons.add(button);

                button.addActionListener(actionListeners.get(actionListeners.size() - 1));

                panel0.add(button);
                panel0.add(new JLabel(""));

            }
            else {
                JButton buttonDown = new JButton("⇓");
                buttonDown.setName(Integer.toString(indexNiveau));
                buttonDown.addActionListener(actionListeners.get(actionListeners.size() - 1));
                buttons.add(buttonDown);

                JButton buttonUp = new JButton("⇑");
                buttonUp.setName(Integer.toString(indexNiveau));
                buttonUp.addActionListener(actionListeners.get(actionListeners.size() - 1));
                buttons.add(buttonUp);

                panel0.add(buttonDown);
                panel0.add(buttonUp);
            }







            panel0.add(new JLabel(""));
            panel0.add(new JLabel(""));

        }

        actionListeners.add(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Level: " + ((JButton)(e.getSource())).getName() + " needs to go : " + e.getActionCommand());

            }
        });

        panel0.add(new JLabel(""));

        JButton button = new JButton("⇑");
        button.setName("Rc");
        buttons.add(button);
        button.addActionListener(actionListeners.get(actionListeners.size() - 1));

        panel0.add(button);
        panel.add(panel0);





        return panel;

    }
    */

    /**
     * <p> Cree la representation visuelle interactive des boutons externes a la cabine.
     * </p>
     */

    private JPanel createCallButtons() {

        JPanel panel = new JPanel();
        JPanel panel0 = new JPanel(new GridLayout((numberOfLevels * 2) - 1, 1));

        panel.setBackground(Color.WHITE);
        panel0.setBackground(Color.GRAY);

        ArrayList<JButton> buttons = new ArrayList<>();
        ArrayList<ActionListener> actionListeners = new ArrayList<>();

        for (int indexNiveau = numberOfLevels - 1; indexNiveau >= 0; indexNiveau--) {

            if (indexNiveau == 0)
                buttons.add(new JButton("Rc"));
            else
                buttons.add(new JButton(Integer.toString(indexNiveau)));


            actionListeners.add(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int niveau;

                    if (e.getActionCommand() == "Rc")
                        niveau = 0;
                    else
                        niveau = Integer.valueOf(e.getActionCommand());

                    buttons.get(numberOfLevels - 1 - niveau).setBackground(Color.red);
                    command_control.addDestination(niveau);

                }
            });


            buttons.get(buttons.size() - 1).addActionListener(actionListeners.get(actionListeners.size() - 1));


            panel0.add(buttons.get(buttons.size() - 1));
            if (indexNiveau != 0)
                panel0.add(new JLabel(" "));


        }


        buttons.get(buttons.size() - 1).addActionListener(actionListeners.get(actionListeners.size() - 1));

        callButtons = buttons;

        panel.add(panel0);

        return panel;

    }

    /**
     * <p> Cree la representation visuelle interactive des boutons internes a la cabine.
     * </p>
     *
     */

    private JPanel createInsideButtons() { // boutons a droite
        JPanel panel = new JPanel(new GridLayout(2, 1));

        JPanel panel0 = new JPanel(new GridLayout(numberOfLevels / 2 + 1, 2));

        panel.setBackground(Color.WHITE);
        panel0.setBackground(Color.GRAY);

        ArrayList<JButton> buttons = new ArrayList<>();
        ArrayList<ActionListener> actionListeners = new ArrayList<>();

        for (int indexNiveau = numberOfLevels - 1; indexNiveau >= 0; indexNiveau--) {

            if (indexNiveau == 0)
                buttons.add(new JButton("Rc"));
            else
                buttons.add(new JButton(Integer.toString(indexNiveau)));


            actionListeners.add(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int niveau;

                    if (e.getActionCommand() == "Rc")
                        niveau = 0;
                    else
                        niveau = Integer.valueOf(e.getActionCommand());

                    buttons.get(numberOfLevels - 1 - niveau).setBackground(Color.red);
                    command_control.addDestination(niveau);

                }
            });


            buttons.get(buttons.size() - 1).addActionListener(actionListeners.get(actionListeners.size() - 1));


            panel0.add(buttons.get(buttons.size() - 1));
        }


        buttons.add(new JButton("!"));


        actionListeners.add(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("actionPerformed: emergencyStop();");
                command_control.emergencyStop();
            }
        });


        buttons.get(buttons.size() - 1).addActionListener(actionListeners.get(actionListeners.size() - 1));

        JPanel panel1 = new JPanel(new GridLayout(1, 1));

        panel1.setBackground(Color.GRAY);
        panel1.add(buttons.get(buttons.size() - 1));

        /*
        JLabel emptySpaceInvisibleLabel = new JLabel();
        emptySpaceInvisibleLabel.setVisible(false);
        panel1.add(emptySpaceInvisibleLabel);
        */

        buttons.get(buttons.size() - 1).setAlignmentY(CENTER_ALIGNMENT);

        panel.add(panel0);

        /*
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.GRAY);
        panel.add(emptyPanel);
        */

        insideButtons = buttons;

        panel.add(panel1);

        return panel;
    }

    /**
     * <p> Cree le bouton qui fait avancer le temps d'une etape.
     * </p>
     */

    private JPanel createStepButton() {
        JPanel panel = new JPanel();
        JPanel panel0 = new JPanel();
        panel.add(panel0);

        JButton button = new JButton("NEXT STEP");

        panel.setBackground(Color.WHITE);
        panel0.setBackground(Color.GRAY);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operative_part.refreshStep();

                stopped = operative_part.isStopped();

                command_control.refreshStep();

                refreshStep();
            }
        });

        panel0.add(button);

        return panel;

    }


    /**
     * <p> Met a jour elevator d'une etape.
     * </p>
     *
     */

    public void refreshStep() {
        elevator.setValue(elevatorDisplayBottomMargin + (int) operative_part.getElevatorPosition());

        if (stopped)  {

            insideButtons.get(insideButtons.size() - 2 - command_control.getCurrentLevel()).setBackground(defaultButton.getBackground());
            callButtons.get(callButtons.size() - 1 - command_control.getCurrentLevel()).setBackground(defaultButton.getBackground());


        }
    }


    /**
     * <p> Getter de levelSizeHeight.
     * </p>
     *
     * @return le nombre de pixels entre chaque niveau.
     */

    public int getLevelSizeHeight() {
        return levelSizeHeight;
    }


    /**
     * <p> Getter de elevator.
     * </p>
     *
     * @return la representation visuelle de l'ascenseur.
     */

    public JSlider getElevator() {
        return elevator;
    }
}
