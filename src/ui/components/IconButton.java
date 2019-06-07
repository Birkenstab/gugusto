package ui.components;

import ui.icon.Icon;
import util.Size;
import util.Vector;

public class IconButton extends ImageButton {

    private static final Size defaultSize = new Size(Icon.DEFAULT_DRAW_SIZE, Icon.DEFAULT_DRAW_SIZE);

    public IconButton(Vector position, Icon icon){
        super(position, defaultSize, Icon.get(icon).getIcon(), Icon.get(icon).getHoverIcon());
    }

    public IconButton(Vector position, int size, Icon icon){
        super(position, new Size(size, size), Icon.get(icon).getIcon(), Icon.get(icon).getHoverIcon());
    }

    public IconButton(Vector position, Size size, Icon icon){
        super(position, size, Icon.get(icon).getIcon(), Icon.get(icon).getHoverIcon());
    }

}
