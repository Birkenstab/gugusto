package graphic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public final class TextureLoader {

    private static Map<Texture, BufferedImage> textures;

    static {
        textures = new HashMap<>();

        File grassBlock = new File("./Gugusto Graphics/grass.png");
        File coin = new File("./Gugusto Graphics/coin.png");
        File saw = new File("./Gugusto Graphics/saw.png");
        File icons = new File("./Gugusto Graphics/icons.png");
        File buttonSections = new File("./Gugusto Graphics/button_sections.png");
        File playerIdle = new File("./Gugusto Graphics/idle.png");
        File playerJumpFall = new File("./Gugusto Graphics/jumpfall.png");
        File playerWalk = new File("./Gugusto Graphics/walk.png");

        try {
            textures.put(Texture.BLOCK_GRASS, ImageIO.read(grassBlock));
            textures.put(Texture.BLOCK_COIN, ImageIO.read(coin));

            textures.put(Texture.ENEMY_SAW, ImageIO.read(saw));

            textures.put(Texture.PLAYER_IDLE, ImageIO.read(playerIdle));
            textures.put(Texture.PLAYER_JUMP_FALL, ImageIO.read(playerJumpFall));
            textures.put(Texture.PLAYER_WALK, ImageIO.read(playerWalk));

            textures.put(Texture.ICONS, ImageIO.read(icons));
            textures.put(Texture.BUTTON_SECTIONS, ImageIO.read(buttonSections));
        } catch (IOException e) {
            e.printStackTrace();
        }

        validate();
    }

    private static void validate(){
        for(Texture texture : EnumSet.allOf(Texture.class)){
            if(!textures.containsKey(texture) || textures.get(texture) == null){
                throw new Error("Failed to load texture: " + texture);
            }
        }
    }

    private TextureLoader(){}

    public static BufferedImage get(Texture texture){
        return textures.get(texture);
    }

}
