package de.thu.gpro.gugusto.util;

import java.text.DecimalFormat;

public class DebugInfo {
    public static int activeDynamicGameObjects;
    public static int inactiveDynamicGameObjects;
    public static boolean running;
    public static Vector cameraPos;
    public static int checkedStaticCollisions;
    public static int occurredStaticCollisions;
    public static int checkedDynamicCollisions;
    public static int occurredDynamicCollisions;
    public static int visibleChunks;
    public static double avgLevelLogicUpdateTime;

    public static String toText() {
        return "activeDynamicGameObjects: " + activeDynamicGameObjects +
                "\ninactiveDynamicGameObjects: " + inactiveDynamicGameObjects +
                "\nrunning: " + running +
                "\ncameraPos: " + cameraPos +
                "\ncheckedStaticCollisions: " + checkedStaticCollisions +
                "\noccurredStaticCollisions: " + occurredStaticCollisions +
                "\ncheckedDynamicCollisions: " + checkedDynamicCollisions +
                "\noccurredDynamicCollisions: " + occurredDynamicCollisions +
                "\nvisibleChunks: " + visibleChunks +
                "\navgLevelLogicUpdateTime: " + new DecimalFormat("0.00µs").format(avgLevelLogicUpdateTime)
                ;
    }
}
