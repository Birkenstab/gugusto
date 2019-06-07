package ui.icon;

import util.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

final class IconUtil {

    private static BufferedImage iconSpriteSheet;
    private static Map<Icon, HoverIcon> icons = new HashMap<>();

    static {
        File file = new File(".\\Gugusto Graphics\\icons.png");
        try {
            iconSpriteSheet = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

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
        return iconSpriteSheet.getSubimage((int)position.getX(), (int)position.getY(), Icon.SIZE, Icon.SIZE);
    }

}
