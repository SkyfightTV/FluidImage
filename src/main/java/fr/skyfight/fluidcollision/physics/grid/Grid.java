package fr.skyfight.fluidcollision.physics.grid;

import fr.skyfight.fluidcollision.Settings;
import fr.skyfight.fluidcollision.objects.Water;
import fr.skyfight.fluidcollision.utils.Location;
import fr.skyfight.fluidcollision.utils.Vector2D;

import java.util.List;

public class Grid {
    public static Cell[][] grid;
    public static final int CELL_SIZE = 20;

    public Grid() {
        grid = new Cell[Settings.WIDTH / CELL_SIZE + 1][Settings.HEIGHT / CELL_SIZE + 1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void updateGrid(List<Water> activeWaters) {
        for (Cell[] cells : grid)
            for (Cell cell : cells)
                cell.clear();
        activeWaters.forEach(water -> {
            Location location = water.getLocation();
            int x = (int) (location.getX() / CELL_SIZE);
            int y = (int) (location.getY() / CELL_SIZE);
            if (x >= grid.length)
                x = grid.length - 1;
            if (y >= grid[0].length)
                y = grid[0].length - 1;
            if (x < 0)
                x = 0;
            if (y < 0)
                y = 0;
            grid[x][y].addWater(water);
        });
    }

    public void solveCollisions(List<Water> activeWaters) {
        //Use grid
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                Cell cell = grid[x][y];
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (x + dx >= 0 && x + dx < Settings.WIDTH/CELL_SIZE && y + dy >= 0 && y + dy < Settings.HEIGHT/CELL_SIZE) {
                            Cell o = grid[x + dx][y + dy];
                            for (Water water1 : cell.getWaters()) {
                                for (Water water2 : o.getWaters()) {
                                    if (water1 != water2) {
                                        Vector2D to_obj = new Vector2D(water1.getLocation(), water2.getLocation());
                                        float distance = to_obj.getMagnitude();
                                        if (distance < water1.getRadius() + water2.getRadius()) {
                                            to_obj.normalize();
                                            float delta = (water1.getRadius() + water2.getRadius()) - distance;
                                            water1.getLocation().add(0.5f * delta * to_obj.getX(), 0.5f * delta * to_obj.getY());
                                            water2.getLocation().remove(0.5f * delta * to_obj.getX(), 0.5f * delta * to_obj.getY());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
