package de.thu.gpro.gugusto.ui.components;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultilineLabel extends UIComponent {
    private List<Label> labels;
    private Vector padding;
    private Font font;
    private int width;

    public MultilineLabel(Vector position, int width, String text, Vector padding, Font font) {
        super(position, new Size());
        this.padding = padding;
        this.font = font;
        this.width = width;
        createLabels(text);

        double height = 0;
        for (Label label : labels) {
            Size size = label.getBoundingBox().getSize();
            height += size.getHeight();
        }
        boundingBox.getSize().set(width, height);
    }

    public void setColor(Color color) {
        labels.forEach(label -> label.setColor(color));
    }

    public void setPosition(Vector position) {
        boundingBox.getPosition().set(position);
        updatePositions();
    }

    private void createLabels(String text) {
        labels = Arrays.stream(text.split("\n"))
                .map(line -> new Label(new Vector(), line, width, padding, font, Label.Alignment.CENTER))
                .collect(Collectors.toList());
        addUIComponent(new ArrayList<>(labels));
        updatePositions();
    }


    private void updatePositions() {
        double y = 0;
        for (Label label : labels) {
            label.getBoundingBox().getPosition().set(boundingBox.getPosition().clone().add(new Vector(0, y)));
            y += label.getBoundingBox().getSize().getHeight();
        }
    }
}
