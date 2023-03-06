package fr.skyfight.fluidcollision.utils;

import fr.skyfight.fluidcollision.Application;
import fr.skyfight.fluidcollision.Settings;
import fr.skyfight.fluidcollision.objects.Water;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Saver {
    public static void save(List<Water> activeWaters) {
        if (!Settings.saveFile.exists()) {
            try {
                Settings.saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.write(Settings.saveFile.toPath(), "".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Saving " + Settings.waterCount + " waters");
        activeWaters.forEach(water -> {
            try {
                StringBuilder str = new StringBuilder();
                Location location = water.getLocation();
                float radius = water.getRadius();
                int x = (int) location.getX();
                int y = (int) location.getY();
                for (int i = x - (int) radius; i < x + radius; i++) {
                    for (int j = y - (int) radius; j < y + radius; j++) {
                        double dx = i - x;
                        double dy = j - y;
                        double distanceSquared = dx * dx + dy * dy;
                        if (distanceSquared > radius * radius) continue;
                        if (i < 0 || j < 0 || i >= Settings.WIDTH || j >= Settings.HEIGHT)
                            continue;
                        int index = i + j * Settings.WIDTH;
                        str.append(index).append(" ");
                    }
                }
                str.append("\n");
                Files.write(Settings.saveFile.toPath(), str.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
