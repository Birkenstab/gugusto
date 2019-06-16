package de.thu.gpro.gugusto.ui.components.button;

import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TextButton extends Button {

    private static final int SECTION_WIDTH = 61;
    private static final int SECTION_HEIGHT = 128;
    private static final float SECTION_RATIO = 61 / 128f;

    private static final FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

    private static Image sectionLeft;
    private static Image sectionMiddle;
    private static Image sectionRight;

    private static Image sectionHoverLeft;
    private static Image sectionHoverMiddle;
    private static Image sectionHoverRight;

    private Font font;
    private String label;
    private Vector padding;
    private Integer fontBaseline;

    //TODO: Asset loader
    static {
        BufferedImage buttonSections = TextureLoader.get(Texture.BUTTON_SECTIONS);
        sectionLeft = buttonSections.getSubimage(0, 0, SECTION_WIDTH, SECTION_HEIGHT);
        sectionMiddle = buttonSections.getSubimage(SECTION_WIDTH, 0, SECTION_WIDTH, SECTION_HEIGHT);
        sectionRight = buttonSections.getSubimage(SECTION_WIDTH * 2, 0, SECTION_WIDTH, SECTION_HEIGHT);

        sectionHoverLeft = buttonSections.getSubimage(0, SECTION_HEIGHT, SECTION_WIDTH, SECTION_HEIGHT);
        sectionHoverMiddle = buttonSections.getSubimage(SECTION_WIDTH, SECTION_HEIGHT, SECTION_WIDTH, SECTION_HEIGHT);
        sectionHoverRight = buttonSections.getSubimage(SECTION_WIDTH * 2, SECTION_HEIGHT, SECTION_WIDTH, SECTION_HEIGHT);
    }

    private static Size calcSize(String text, Vector padding, Font font){
        Rectangle2D rect = font.getStringBounds(text, frc);
        Vector padded = new Vector(rect.getWidth(), rect.getHeight()).add(padding.clone().multiply(2));
        return new Size(padded);
    }

    TextButton(Vector position, String label, Vector padding, Font font) {
        super(position, calcSize(label, padding, font));
        this.label = label;
        this.font = font;
        this.padding = padding;
    }

    TextButton(Vector position, String label, int width, Vector padding, Font font) {
        super(position, calcSize(label, padding, font));
        this.label = label;
        this.font = font;
        this.padding = new Vector((width - boundingBox.getSize().getWidth()) / 2, padding.getY());
        boundingBox.getSize().setWidth(width);
    }

    @Override
    public void draw(Graphics2D g2d){
        drawBackground(g2d);
        drawText(g2d);
    }

    private void drawBackground(Graphics2D g2d){
        int sectionWidth = (int)(getHeight() * SECTION_RATIO);
        Image left = sectionLeft;
        Image middle = sectionMiddle;
        Image right = sectionRight;

        if(hover){
            left = sectionHoverLeft;
            middle = sectionHoverMiddle;
            right = sectionHoverRight;
        }

        g2d.drawImage(left, getX(), getY(), sectionWidth, getHeight(), null);
        g2d.drawImage(middle, getX() + sectionWidth, getY(), getWidth() - sectionWidth * 2, getHeight(), null);
        g2d.drawImage(right, getX() + getWidth() - sectionWidth, getY(), sectionWidth, getHeight(), null);
    }

    private void drawText(Graphics2D g2d){
        if(fontBaseline == null) fontBaseline = g2d.getFontMetrics(font).getAscent();

        g2d.setColor(Color.WHITE);
        g2d.setFont(font);

        int x = (int)(getX() + padding.getX());
        int y = (int)(getY() + fontBaseline + padding.getY());
        g2d.drawString(label, x, y);
    }

}
