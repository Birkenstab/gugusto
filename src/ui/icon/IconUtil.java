package ui.icon;

import graphic.Texture;
import graphic.TextureLoader;
import util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
