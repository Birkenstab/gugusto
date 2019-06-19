package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;

final public class LabelFactory {

    private static final Vector defaultPadding = new Vector(20, 10);
    private static final String defaultFontName = "Arial";

    private Integer width;
    private Vector padding;
    private Font font;
    private Label.Alignment alignment = Label.Alignment.CENTER;

    public LabelFactory(){}

    public LabelFactory(Vector padding, int fontSize){
        this(padding, new Font(defaultFontName, Font.PLAIN, fontSize));
    }

    public LabelFactory(Vector padding, int fontSize, Label.Alignment alignment){
        this(padding, new Font(defaultFontName, Font.PLAIN, fontSize));
        this.alignment = alignment;
    }

    public LabelFactory(int width, Vector padding, int fontSize, Label.Alignment alignment){
        this(width, padding, new Font(defaultFontName, Font.PLAIN, fontSize));
        this.alignment = alignment;
    }

    public LabelFactory(int width, Vector padding, int fontSize){
        this(width, padding, new Font(defaultFontName, Font.PLAIN, fontSize));
    }

    public LabelFactory(Vector padding, Font font){
        this.padding = padding;
        this.font = font;
    }

    public LabelFactory(int width, Vector padding, Font font){
        this(width, padding, font, Label.Alignment.CENTER);
    }

    public LabelFactory(int width, Vector padding, Font font, Label.Alignment alignment){
        this.width = width;
        this.padding = padding;
        this.font = font;
        this.alignment = alignment;
    }

    public Label create(Vector position, String text){
        if(width == null) return new Label(position, text, padding, font);
        return new Label(position, text, width, padding, font, alignment);
    }

}
