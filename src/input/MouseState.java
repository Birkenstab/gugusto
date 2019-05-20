package input;

import util.Vector;

import java.util.HashMap;
import java.util.Map;

public class MouseState {

    private static Map<Integer, Boolean> state = new HashMap<>();
    private static Vector position = new Vector(-1, -1);

    public static boolean isDown(int button){
        Boolean bool = state.get(button);
        return bool == null ? false : bool;
    }
    public static Vector getPosition(){
        return position;
    }

    public static void set(int button, boolean isDown){
        state.put(button, isDown);
    }
    public static void set(int x, int y){
        position.set(x, y);
    }

}
