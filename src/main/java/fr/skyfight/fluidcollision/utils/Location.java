package fr.skyfight.fluidcollision.utils;

public class Location extends Vector2D {
    public Location(float x, float y) {
        super(x, y);
    }

    public Location(Location location, Location lastLocation) {
        super(location, lastLocation);
    }

    public Location(Vector2D vector2D, Vector2D lastVector2D) {
        super(vector2D, lastVector2D);
    }

    public Location() {
    }
}
