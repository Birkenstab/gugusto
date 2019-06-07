package ui.icon;

import java.awt.*;

public class HoverIcon {

    private Image icon;
    private Image hoverIcon;

    public HoverIcon(Image icon, Image hoverIcon){
        this.icon = icon;
        this.hoverIcon = hoverIcon;
    }

    public Image getIcon(){
        return icon;
    }

    public Image getHoverIcon(){
        return hoverIcon;
    }

}
