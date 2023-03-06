package fr.skyfight.fluidcollision;

import fr.skyfight.fluidcollision.utils.Vector2D;
import processing.core.PImage;

import java.io.File;
import java.text.DecimalFormat;

public class Settings {
    public static final DecimalFormat DF = new DecimalFormat("0.00");
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int OBJECTS = 2700;
    public static final int OBJECT_DIAMETER = 20;
    public static final Vector2D GRAVITY = new Vector2D(0, 2/9.81f);
    public static final int STEPS = 8;
    public static final float DT = 1f / STEPS;

    public static String path;
    public static boolean save = true;
    public static File saveFile;
    public static int waterCount = 0;
    public static File image;
    public static PImage img;
    public static long lastTime = System.currentTimeMillis();
}
