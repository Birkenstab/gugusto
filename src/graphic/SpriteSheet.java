package graphic;

import game.object.Direction;

import java.awt.image.BufferedImage;

public final class SpriteSheet {

    private SpriteSheet(){}

    public static BufferedImage[] extract(BufferedImage image, int nX, int nY, int width, int height){
        return extract(image, nX, nY, width, height, 0, 0);
    }

    public static BufferedImage[] extract(BufferedImage image, int nX, int nY, int width, int height, int oX, int oY){
        BufferedImage[] images = new BufferedImage[nX * nY];

        for(int x = 0; x < nX; x++){
            for(int y = 0; y < nY; y++){
                images[x + y * width] = image.getSubimage(x * (width + oX), y * (height + oY), width, height);
            }
        }

        return images;
    }

}
