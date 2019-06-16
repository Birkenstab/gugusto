package util;

public class DebugInfo {
    public static int activeDynamicGameObjects;
    public static int inactiveDynamicGameObjects;
    public static boolean running;
    public static Vector cameraPos;

    public static String toText() {
        return "activeDynamicGameObjects: " + activeDynamicGameObjects +
                "\ninactiveDynamicGameObjects: " + inactiveDynamicGameObjects +
                "\nrunning: " + running +
                "\ncameraPos: " + cameraPos
                ;
    }
}
