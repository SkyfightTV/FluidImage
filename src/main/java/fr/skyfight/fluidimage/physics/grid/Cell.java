package fr.skyfight.fluidimage.physics.grid;

import fr.skyfight.fluidimage.objects.Water;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final List<Water> waters;

    public Cell() {
        waters = new ArrayList<>();
    }

    public void clear() {
        waters.clear();
    }

    public List<Water> getWaters() {
        return waters;
    }

    public void addWater(Water water) {
        waters.add(water);
    }

    public void removeWater(Water water) {
        waters.remove(water);
    }
}
