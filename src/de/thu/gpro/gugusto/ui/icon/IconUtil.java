package de.thu.gpro.gugusto.ui.icon;

import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

final class IconUtil {

    private static final BufferedImage texture = TextureLoader.get(Texture.ICONS);
    private static Map<Icon, HoverIcon> icons = new HashMap<>();

    private IconUtil(){}


    static HoverIcon getIcon(Icon icon){
        if(icons.containsKey(icon)) return icons.get(icon);

        Vector position = icon.getPosition();
        Image iconImage = subImage(position);
        Image hoverIconImage = subImage(position.add(new Vector(Icon.SIZE, 0)));
        HoverIcon hoverIcon = new HoverIcon(iconImage, hoverIconImage);

        icons.put(icon, hoverIcon);

        return hoverIcon;
    }

    private static Image subImage(Vector position){
        return texture.getSubimage((int)position.getX(), (int)position.getY(), Icon.SIZE, Icon.SIZE);
    }

}
