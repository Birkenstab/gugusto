package input;

import java.util.HashMap;
import java.util.Map;

public class KeyState {

    private static Map<Integer, Boolean> state = new HashMap<>();

    public static boolean isDown(char key){
        return isDown(key | 0x20);
    }

    public static boolean isDown(int keyCode){
        Boolean bool = state.get(keyCode);
        return bool == null ? false : bool;
    }

    public static void set(int keyCode, boolean isDown){
        state.put(keyCode | 0x20, isDown);
    }

}
