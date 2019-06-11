package ui.components.button;

import game.Camera;
import util.Size;
import util.Vector;

import java.awt.*;

public class ImageButton extends Button {

    private Image image;
    private Image hoverImage;

    public ImageButton(Vector position, Size size, Image image, Image hoverImage){
        super(position, size);
        this.image = image;
        this.hoverImage = hoverImage;
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera) {
        super.draw(g2d, camera);

        Image img = image;
        if(hover) img = hoverImage;
        g2d.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
    }

}
