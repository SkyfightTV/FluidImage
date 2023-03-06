package fr.skyfight.fluidcollision.objects;

import fr.skyfight.fluidcollision.Settings;
import fr.skyfight.fluidcollision.utils.Location;
import fr.skyfight.fluidcollision.Main;
import fr.skyfight.fluidcollision.utils.Vector2D;
import processing.core.PApplet;

import java.util.Random;

public class Water {
    private Location location;
    private Location lastLocation;
    private Vector2D acceleration;
    private Vector2D velocity;

    private float diameter;
    private float radius;

    private int []pixels;

    public Water(Location location, float diameter, Vector2D acceleration) {
        this.location = location;
        this.lastLocation = location;
        this.diameter = diameter;
        this.radius = diameter / 2;
        this.acceleration = acceleration;
        velocity = new Vector2D();
        pixels = null;
    }

    public void update(PApplet p, float dt) {
        p.noStroke();
        velocity.set(location, lastLocation);
        lastLocation = location;
        location = new Location(location.getX() + velocity.getX(), location.getY() + velocity.getY());
        location.addY(acceleration.getY() * dt * dt);
        location.addX(acceleration.getX() * dt * dt);
        acceleration.reset();

        int x = (int) location.getX();
        int y = (int) location.getY();
        int index = 0;
        for (int i = (int) (x - radius); i < x + radius; i++) {
            for (int j = (int) (y - radius); j < y + radius; j++) {
                double dx = i - x;
                double dy = j - y;
                double distanceSquared = dx * dx + dy * dy;
                if (i < 0 || i >= Settings.WIDTH || j < 0 || j >= Settings.HEIGHT) continue;
                if (distanceSquared > radius * radius) continue;
                if (pixels != null) {
                    if (index >= pixels.length)
                        continue;
                    int color = Settings.img.pixels[pixels[index]];
                    p.set(i, j, color);
                    index++;
                } else {
                    index = i + j * Settings.WIDTH;
                    int color = Settings.img.pixels[index];
                    if (color != 0) {
                        p.set(i, j, color);
                    }
                }
            }
        }
    }

    public void apply_force(Vector2D force) {
        acceleration.addX(force.getX());
        acceleration.addY(force.getY());
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getDiameter() {
        return diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public float getRadius() {
        return radius;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public void setAcceleration(Vector2D acceleration) {
        this.acceleration = acceleration;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
}
