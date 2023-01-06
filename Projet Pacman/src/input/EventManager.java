package input;


import java.util.ArrayList;


public class EventManager {


    ArrayList <Object> arguments = new ArrayList <Object>();

    Runnable manager = () -> {};


    public void run() {

        manager.run();

        resetArguments();

    }


    public void setArguments (ArrayList <Object> arguments) {
        this.arguments = arguments;
    }

    public void resetArguments() { arguments = new ArrayList<Object>(); }


    public Runnable getManager() {
        return manager;
    }

    public void setManager(Runnable manager) {
        this.manager = manager;
    }

    public ArrayList <Object> getArguments() {
        return arguments;
    }

}
