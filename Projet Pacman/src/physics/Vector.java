package physics;

public class Vector {

    // les vecteurs n'ont pas de coordonnées de départ
    // on part du principe que les vecteurs partent de l'origine (0,0)
    private Coordinates endPoint;


    public Vector(Coordinates endPoint) {
        this.endPoint = endPoint;
    }

    // Ce constructeur prend deux coordonnées et trouve le vecteur qui fait le lien entre le 1er et le 2eme
    public Vector(Coordinates start, Coordinates end) {
        this.endPoint = new Coordinates(end.getX() - start.getX(), end.getY() - start.getY());
    }

    public Vector(double x, double y) {
        this.endPoint = new Coordinates(x, y);
    }

    public Vector normalize () {

        if (getNorm() != 0)
            return new Vector(endPoint.getX()/getNorm(), endPoint.getY()/getNorm());

        return new Vector(0,0);

    }

    // cette méthode modifie la taille du vecteur en multipliant par un scalaire
    public Vector scale(double scale) {
        return new Vector(endPoint.getX() * scale, endPoint.getY() * scale);
    }

    // cette méthode donne la norme du vecteur (sa longueur qui peut correspondre a la vitesse d'un objet)
    public double getNorm() {
        return Math.sqrt(endPoint.getX() * endPoint.getX() + endPoint.getY() * endPoint.getY());
    }

    // getter
    public Coordinates getEndPoint() {
        return endPoint;
    }

    @Override
    public String toString() {
        return getEndPoint().toString();
    }
}
