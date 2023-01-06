package physics;


import java.util.ArrayList;

import static physics.Coordinates.doubleEquals;


public class PhysicalElement {


    private static ArrayList<PhysicalElement> physicalElementsList = new ArrayList<>();

    private Coordinates coordinates;

    private CollisionBox collisionBox;

    private boolean reactsOnCollision = false;

    private double speed = 0;

    private Vector vector = new Vector(0,0);


    private boolean isEnabled = true;

    private boolean movedFlag = false;

    private boolean collisionFlag = false;


    public PhysicalElement (Coordinates coordinates, CollisionBox collisionBox) {
        this.coordinates = coordinates;
        this.collisionBox = collisionBox;

        physicalElementsList.add(this);

    }


    public PhysicalElement (Coordinates coordinates) {

        this.coordinates = coordinates;

        physicalElementsList.add(this);

    }


    public ArrayList <PhysicalElement> tryToMove() {

        ArrayList <PhysicalElement> collidedWith = new ArrayList<PhysicalElement>();

        Vector originalVector = new Vector (vector.getEndPoint().getX(),vector.getEndPoint().getY());

        if (!isEnabled)
            return collidedWith;


        boolean collidesWithRigid = false;


        for (PhysicalElement otherElement : physicalElementsList) {


            if (otherElement.isEnabled && otherElement != this) {


                if (wouldCollide(otherElement, originalVector)) {


                    collisionFlag = true;

                    collidedWith.add(otherElement);


                    if (isRigid() && otherElement.isRigid()) {

                        collidesWithRigid = true;

                        setVector(new Vector(0, 0));

                    }


                }



            }

        }

        //Coordinates oldCoordinates = new Coordinates(coordinates.getX(), coordinates.getY());

        if (!collidesWithRigid && !doubleEquals(vector.getNorm(), 0)) {
            movedFlag = true;
            setCoordinates(getCoordinates().nextCoordinates(getVector()));
        }

        return collidedWith;
    }


    public boolean wouldCollide (PhysicalElement obstacleElement, Vector vector) {

        Coordinates startTopLeft = getBox().getCoordinates();
        Coordinates startTopRight = new Coordinates((startTopLeft.getX() + getBox().getWidth()), startTopLeft.getY());
        Coordinates startBottomRight = new Coordinates((startTopLeft.getX() + getBox().getWidth()), (startTopLeft.getY() + getBox().getHeight()));
        Coordinates startBottomLeft = new Coordinates((startTopLeft.getX()), startTopLeft.getY() + getBox().getHeight());


        Coordinates obstacleTopLeft = obstacleElement.getBox().getCoordinates();
        Coordinates obstacleTopRight = new Coordinates((obstacleTopLeft.getX() + obstacleElement.getBox().getWidth()), obstacleTopLeft.getY());
        Coordinates obstacleBottomRight = new Coordinates((obstacleTopLeft.getX() + obstacleElement.getBox().getWidth()), (obstacleTopLeft.getY() + obstacleElement.getBox().getHeight()));
        Coordinates obstacleBottomLeft = new Coordinates((obstacleTopLeft.getX()), obstacleTopLeft.getY() + obstacleElement.getBox().getHeight());

        Coordinates nextPositionTopLeft = startTopLeft.nextCoordinates(vector);
        Coordinates nextPositionTopRight = startTopRight.nextCoordinates(vector);
        Coordinates nextPositionBottomRight = startBottomRight.nextCoordinates(vector);
        Coordinates nextPositionBottomLeft = startBottomLeft.nextCoordinates(vector);

        // si on est tout a gauche de l'obstacle
        if (nextPositionBottomRight.getX() <= obstacleTopLeft.getX())
            return false;

        // si on est tout a droite de l'obstacle
        if (nextPositionBottomLeft.getX() >= obstacleTopRight.getX())
            return false;

        // si on est tout en haut de l'obstacle
        if (nextPositionBottomRight.getY() <= obstacleTopLeft.getY())
            return false;

        // si on est tout en bas de l'obstacle
        if (nextPositionTopLeft.getY() >= obstacleBottomRight.getY())
            return false;

        return true;
    }


    public void setVector(Vector vector) {
        this.vector = vector.normalize();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public CollisionBox getBox() {
        return collisionBox;
    }

    public Vector getVector() {
        return vector;
    }

    public boolean isRigid() {
        return collisionBox.isRigid();
    }


    public void setCoordinates (Coordinates coordinates) {

        collisionBox.setCoordinates(collisionBox.getCoordinates().nextCoordinates(this.coordinates.vectorBetween(coordinates)));
        this.coordinates = coordinates;

    }


    public void setSpeed (double speed) {
        this.speed = speed;
    }


    public void setEnable (boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setReactsOnCollision(boolean reactsOnCollision) {
        this.reactsOnCollision = reactsOnCollision;
    }

    public double getSpeed() {
        return speed;
    }

    public void disable() {
        setEnable(false);
    }

    public void enable() {
        setEnable(true);
    }


    public void setMovedFlag (boolean flag) {
        movedFlag = flag;
    }

    public void setCollisionFlag (boolean flag) {
        collisionFlag = flag;
    }

    public boolean checkCollisionFlag() {

        boolean flag = collisionFlag;

        collisionFlag = false;

        return flag;

    }


    public boolean checkMovedFlag() {

        boolean flag = movedFlag;

        movedFlag = false;

        return flag;

    }


    public void setBox(CollisionBox collisionBox) {
        this.collisionBox = collisionBox;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
