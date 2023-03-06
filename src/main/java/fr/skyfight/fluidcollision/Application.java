package fr.skyfight.fluidcollision;

import fr.skyfight.fluidcollision.objects.Water;
import fr.skyfight.fluidcollision.physics.Physics;
import fr.skyfight.fluidcollision.physics.grid.Grid;
import fr.skyfight.fluidcollision.utils.Location;
import fr.skyfight.fluidcollision.utils.Saver;
import fr.skyfight.fluidcollision.utils.Vector2D;
import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Application extends PApplet {
    public static List<Water> waterList = new ArrayList<>();
    public static Grid grid;

    public void settings() {
        size(Settings.WIDTH, Settings.HEIGHT);
    }

    public void setup() {
        background(0);
        grid = new Grid();
        for (int i = 0; i < Settings.OBJECTS; i++)
            waterList.add(new Water(new Location(100, 100), Settings.OBJECT_DIAMETER, new Vector2D(20, 0)));

        Settings.saveFile = new File(Settings.path + "\\data.txt");
        if (Settings.image == null)
            throw new RuntimeException("Image not found");
        Settings.img = loadImage(Settings.image.getAbsolutePath());
        Settings.img.resize(Settings.WIDTH, Settings.HEIGHT);
        Settings.img.loadPixels();

        if (Settings.saveFile.exists()) {
            try {
                AtomicInteger i = new AtomicInteger();
                Files.lines(Settings.saveFile.toPath()).forEach(line -> {
                    String[] split = line.split(" ");
                    if (i.get() >= waterList.size())
                        return;
                    Water water = waterList.get(i.get());
                    int []pixels = new int[split.length];
                    for (int j = 0; j < split.length; j++)
                        pixels[j] = Integer.parseInt(split[j]);
                    water.setPixels(pixels);
                    i.getAndIncrement();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            Settings.save = false;
        }
    }

    public void draw() {
        background(0);
        long start = System.nanoTime();
        if (Settings.waterCount < waterList.size()) {
            Settings.lastTime = System.currentTimeMillis();
            Settings.waterCount++;
            if (Settings.waterCount > waterList.size()) {
                Settings.waterCount = waterList.size();
            }
        }
        Physics.process(this, waterList.subList(0, Settings.waterCount));
        updatePixels();
        if (keyPressed && Settings.save) {
            System.out.println("Saving waters data...");
            Settings.save = false;
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                noLoop();
                Saver.save(waterList.subList(0, Settings.waterCount));
                exit();
            }).start();
        }

        float time = (System.nanoTime() - start) / 1000000f;
        text("Simulation time: " + Settings.DF.format(time) + "ms", 10, 20);
        text("Objects: " + Settings.waterCount, 10, 30);
    }
}
