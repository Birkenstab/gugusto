package de.thu.gpro.gugusto.ui.components.button;

import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.ui.components.Label;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextButton extends Button {

    private static final int SECTION_WIDTH = 61;
    private static final int SECTION_HEIGHT = 128;
    private static final float SECTION_RATIO = 61 / 128f;

    private static Image sectionLeft;
    private static Image sectionMiddle;
    private static Image sectionRight;

    private static Image sectionHoverLeft;
    private static Image sectionHoverMiddle;
    private static Image sectionHoverRight;

    private static Image sectionDisabledLeft;
    private static Image sectionDisabledMiddle;
    private static Image sectionDisabledRight;

    static {
        BufferedImage buttonSections = TextureLoader.get(Texture.BUTTON_SECTIONS);
        sectionLeft = buttonSections.getSubimage(0, 0, SECTION_WIDTH, SECTION_HEIGHT);
        sectionMiddle = buttonSections.getSubimage(SECTION_WIDTH, 0, SECTION_WIDTH, SECTION_HEIGHT);
        sectionRight = buttonSections.getSubimage(SECTION_WIDTH * 2, 0, SECTION_WIDTH, SECTION_HEIGHT);

        sectionHoverLeft = buttonSections.getSubimage(0, SECTION_HEIGHT, SECTION_WIDTH, SECTION_HEIGHT);
        sectionHoverMiddle = buttonSections.getSubimage(SECTION_WIDTH, SECTION_HEIGHT, SECTION_WIDTH, SECTION_HEIGHT);
        sectionHoverRight = buttonSections.getSubimage(SECTION_WIDTH * 2, SECTION_HEIGHT, SECTION_WIDTH, SECTION_HEIGHT);

        sectionDisabledLeft = buttonSections.getSubimage(0, SECTION_HEIGHT * 2, SECTION_WIDTH, SECTION_HEIGHT);
        sectionDisabledMiddle = buttonSections.getSubimage(SECTION_WIDTH, SECTION_HEIGHT * 2, SECTION_WIDTH, SECTION_HEIGHT);
        sectionDisabledRight = buttonSections.getSubimage(SECTION_WIDTH * 2, SECTION_HEIGHT * 2, SECTION_WIDTH, SECTION_HEIGHT);
    }


    public TextButton(Label label){
        super(label.getBoundingBox().getPosition(), label.getBoundingBox().getSize());
        addUIComponent(label);
        label.setColor(Color.WHITE);
    }

    @Override
    public void draw(Graphics2D g2d){
        drawBackground(g2d);
    }

    private void drawBackground(Graphics2D g2d){
        int sectionWidth = (int)(getHeight() * SECTION_RATIO);
        Image left = sectionLeft;
        Image middle = sectionMiddle;
        Image right = sectionRight;

        if(disabled){
            left = sectionDisabledLeft;
            middle = sectionDisabledMiddle;
            right = sectionDisabledRight;
        } else if(hover){
            left = sectionHoverLeft;
            middle = sectionHoverMiddle;
            right = sectionHoverRight;
        }

        g2d.drawImage(left, getX(), getY(), sectionWidth, getHeight(), null);
        g2d.drawImage(middle, getX() + sectionWidth, getY(), getWidth() - sectionWidth * 2, getHeight(), null);
        g2d.drawImage(right, getX() + getWidth() - sectionWidth, getY(), sectionWidth, getHeight(), null);
    }

}
