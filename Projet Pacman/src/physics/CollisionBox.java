package physics;

public class CollisionBox {

    private Coordinates coordinates;

    private double width;
    private double height;

    private boolean rigidity;


    public CollisionBox(Coordinates coordinates, double width, double height, boolean rigidity) {
        this.coordinates = coordinates;

        this.width = width;
        this.height = height;

        this.rigidity = rigidity;
    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isRigid() {
        return rigidity;
    }


    public void setCoordinates (Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}