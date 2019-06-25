package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Label extends UIComponent {

    public enum Alignment { LEFT, CENTER}

    private static final FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

    private Integer fontBaseline;

    protected Color color = Color.BLACK;
    protected String text;
    protected Font font;
    protected Vector padding;

    public static Rectangle2D getBounds(String text, Font font){
        return font.getStringBounds(text, frc);
    }

    private static Size calcSize(String text, Vector padding, Font font){
        Rectangle2D rect = font.getStringBounds(text, frc);
        Vector padded = new Vector(rect.getWidth(), rect.getHeight()).add(padding.clone().multiply(2));
        return new Size(padded);
    }

    public Label(Vector position, String text, Vector padding, Font font) {
        super(position, calcSize(text, padding, font));
        this.text = text;
        this.font = font;
        this.padding = padding;
    }

    public Label(Vector position, String text, int width, Vector padding, Font font, Alignment alignment) {
        super(position, calcSize(text, padding, font));
        this.text = text;
        this.font = font;
        this.padding = padding;

        if(alignment == Alignment.CENTER){
            this.padding = new Vector((width - boundingBox.getSize().getWidth()) / 2, padding.getY());
        }

        boundingBox.getSize().setWidth(width);
    }

    @Override
    public void draw(Graphics2D g2d){
        super.draw(g2d);

        if(fontBaseline == null) fontBaseline = g2d.getFontMetrics(font).getAscent();

        g2d.setColor(color);
        g2d.setFont(font);

        int x = (int)(getX() + padding.getX());
        int y = (int)(getY() + fontBaseline + padding.getY());
        g2d.drawString(text, x, y);
    }

    public void setPadding(Vector padding){
        this.padding = padding;
    }

    public Vector getPadding(){
        return padding;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public Rectangle2D getBounds(){
        return getBounds(text, font);
    }

}
