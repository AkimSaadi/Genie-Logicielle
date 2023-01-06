package physics;

public class Coordinates {

    private double x;
    private double y;

    public Coordinates (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public Coordinates nextCoordinates(Vector vector) {
        return new Coordinates(x + vector.getEndPoint().getX(), y + vector.getEndPoint().getY());
    }

    public Coordinates addCoordinates(CollisionBox collisionBox) {
        return nextCoordinates(new Vector(collisionBox.getWidth(), collisionBox.getHeight()));
    }


    @Override
    public String toString() {
        return x + " , " + y;
    }

    @Override
    public boolean equals(Object o) {
        // dés-assertisé (ne fournira pas d'erreur ou de mauvaise valeurs)

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Coordinates that = (Coordinates) o;

        return doubleEquals(that.x, this.x) &&
                doubleEquals(that.y, this.y);
    }

    public static boolean doubleEquals(double a, double b) {
        //pas dés-assertisé mais doit etre géré par les méthodes qui l'appel

        if (Double.isInfinite(a) && Double.isInfinite(b))
            if ((a > 0 && b > 0) || (a < 0 && b < 0))
                return true;
            else
                return false;


        return Math.abs(a-b) < 0.0000001;
    }

    public Vector vectorBetween(Coordinates nextCoordinates) {
        double x = nextCoordinates.getX() - getX();
        double y = nextCoordinates.getY() - getY();

        return new Vector(x,y);
    }

}