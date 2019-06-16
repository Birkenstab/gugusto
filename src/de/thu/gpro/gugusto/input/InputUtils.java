package de.thu.gpro.gugusto.input;

import de.thu.gpro.gugusto.util.Vector;

public final class InputUtils {

    private InputUtils(){}

    public static Vector getRelativeMouseDirection(Vector from){
        double diffX = from.getX() - MouseState.getPosition().getX();
        double diffY = from.getY() - MouseState.getPosition().getY();
        double diff = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));

        return new Vector(1 / diff * diffX, 1 / diff * diffY);
    }

}
