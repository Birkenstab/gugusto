package de.thu.gpro.gugusto.graphic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public final class TextureLoader {

    private static Map<Texture, BufferedImage> textures;

    static {
        textures = new HashMap<>();

        Map<Texture, String> map = Map.of(
                Texture.BLOCK_GRASS,  "./Gugusto Graphics/grass.png",
                Texture.BLOCK_COIN, "./Gugusto Graphics/coin.png",

                Texture.ENEMY_SAW, "./Gugusto Graphics/saw.png",
                Texture.ENEMY_GUSTAV_WALK, "./Gugusto Graphics/100x80 Enemy Run.png",

                Texture.PLAYER_IDLE, "./Gugusto Graphics/idle.png",
                Texture.PLAYER_JUMP_FALL, "./Gugusto Graphics/jumpfall.png",
                Texture.PLAYER_WALK, "./Gugusto Graphics/walk.png",

                Texture.ICONS, "./Gugusto Graphics/icons.png",
                Texture.BUTTON_SECTIONS, "./Gugusto Graphics/button_sections.png"
            );

        for (Map.Entry<Texture, String> entry : map.entrySet()) {
            try {
                textures.put(entry.getKey(), ImageIO.read(new File(entry.getValue())));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
