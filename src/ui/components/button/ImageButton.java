package ui.components.button;

import game.Camera;
import util.Size;
import util.Vector;

import java.awt.*;

public class ImageButton extends Button {

    private Image image;
    private Image hoverImage;


    public ImageButton(Vector position, Size size, Image image){
        this(position, size, image, null);
    }

    public ImageButton(Vector position, Size size, Image image, Image hoverImage){
        super(position, size);
        this.image = image;
        this.hoverImage = hoverImage;
    }

    @Override
    public void draw(Graphics2D g2d){
        Image img = image;
        if(hover && hoverImage != null) img = hoverImage;
        g2d.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
    }

}
