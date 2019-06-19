package de.thu.gpro.gugusto.ui.components.input;

import de.thu.gpro.gugusto.util.Vector;

public class NumberField extends TextField {

    public NumberField(Vector position, int width, String placeholder) {
        super(position, width, placeholder);
    }

    @Override
    protected boolean isValidKey(char c){
        return c > 47 && c < 58;
    }

    public int getInt(){
        return Integer.valueOf(value);
    }

}
