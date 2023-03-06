package fr.skyfight.fluidimage;

import fr.skyfight.fluidimage.objects.Water;
import fr.skyfight.fluidimage.physics.Physics;
import fr.skyfight.fluidimage.physics.grid.Grid;
import fr.skyfight.fluidimage.utils.Location;
import fr.skyfight.fluidimage.utils.Saver;
import fr.skyfight.fluidimage.utils.Vector2D;
import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

        float angle = 0;
        boolean first = true;
        for (int i = 0; i < Settings.OBJECTS; i++) {
            Vector2D vec = new Vector2D(0, 0);
            int temp_x = (int) (Settings.OBJECT_DIAMETER * Math.cos(angle)) * 2;
            int temp_y = (int) (Settings.OBJECT_DIAMETER * Math.sin(angle)) * 2;
            if (first)
                angle += 0.1;
            else
                angle -= 0.1;
            if (angle > Math.PI)
                first = false;
            else if (angle < 0)
                first = true;
            vec.set(temp_x, temp_y);
            waterList.add(new Water(new Location((int)(Settings.WIDTH / 2), 100), Settings.OBJECT_DIAMETER, vec));
        }

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
        if (Settings.waterCount == waterList.size() && !Settings.finish) {
            Settings.finish = true;
            if (Settings.save) {
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
                    try {
                        Main.restartApplication();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } else {
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    noLoop();
                }).start();
            }
        }

        float time = (System.nanoTime() - start) / 1000000f;
        text("Simulation time: " + Settings.DF.format(time) + "ms", 10, 20);
        text("Objects: " + Settings.waterCount, 10, 30);
        String topmsg = "Create model in progress...";
        if (!Settings.save) {
            topmsg = "Reproduice model in progress...";
        }
        text(topmsg, 10, 40, Settings.WIDTH - 20, Settings.HEIGHT - 20);
    }
}
