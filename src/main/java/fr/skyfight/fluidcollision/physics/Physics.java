package fr.skyfight.fluidcollision.physics;

import fr.skyfight.fluidcollision.Application;
import fr.skyfight.fluidcollision.Settings;
import fr.skyfight.fluidcollision.objects.Water;
import fr.skyfight.fluidcollision.utils.Location;
import processing.core.PApplet;

import java.util.List;

public class Physics {
    public static void process(PApplet p, List<Water> activeWaters) {
        Application.grid.updateGrid(activeWaters);
        activeWaters.forEach(water -> water.apply_force(Settings.GRAVITY));
        for (int i = 0; i < Settings.STEPS; i++) {
            applyContraints(activeWaters);
            Application.grid.solveCollisions(activeWaters);
            Application.grid.updateGrid(activeWaters);
        }
        activeWaters.forEach(water -> water.update(p, 1));
    }

    public static void applyContraints(List<Water> activeWaters) {
        activeWaters.forEach(water -> {
            Location loc = water.getLocation();
            if (loc.getX() < water.getRadius()) {
                loc.setX(water.getRadius());
            }
            if (loc.getX() > Settings.WIDTH - water.getRadius()) {
                loc.setX(Settings.WIDTH - water.getRadius());
            }
            if (loc.getY() < water.getRadius()) {
                loc.setY(water.getRadius());
                water.getAcceleration().setY(0);
            }
            if (loc.getY() > Settings.HEIGHT - water.getRadius()) {
                loc.setY(Settings.HEIGHT - water.getRadius());
            }
        });
    }
}
