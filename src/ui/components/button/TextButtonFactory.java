package ui.components.button;

import util.Vector;

import java.awt.*;

final public class TextButtonFactory {

    private static final Vector defaultPadding = new Vector(20, 10);
    private static final String defaultFontName = "Arial";

    private Integer width;
    private Vector padding;
    private Font font;

    public TextButtonFactory(){}

    public TextButtonFactory(Vector padding, int fontSize){
        this.padding = padding;
        font = new Font(defaultFontName, Font.PLAIN, fontSize);
    }

    public TextButtonFactory(int width, Vector padding, int fontSize){
        this.width = width;
        this.padding = padding;
        font = new Font(defaultFontName, Font.PLAIN, fontSize);
    }

    public TextButtonFactory(Vector padding, Font font){
        this.padding = padding;
        this.font = font;
    }

    public TextButtonFactory(int width, Vector padding, Font font){
        this.width = width;
        this.padding = padding;
        this.font = font;
    }

    public TextButton create(Vector position, String text){
        if(width == null) return new TextButton(position, text, padding, font);
        return new TextButton(position, text, width, padding, font);
    }

}
