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


        Map<Texture, String> map = new HashMap<>();

        map.put(Texture.BLOCK_GRASS,  "./Gugusto Graphics/grass.png");
        map.put(Texture.BLOCK_COIN, "./Gugusto Graphics/coin.png");
        map.put(Texture.BLOCK_CHEST, "./Gugusto Graphics/other/NEU Chest Animation.png");

        map.put(Texture.ENEMY_SAW, "./Gugusto Graphics/saw.png");
        map.put(Texture.ENEMY_GUSTAV_WALK, "./Gugusto Graphics/100x80 Enemy Run.png");
        map.put(Texture.ENEMY_VERFOLGI_WALK, "./Gugusto Graphics/Enemy Yellow Run.png");

        map.put(Texture.PLAYER_IDLE, "./Gugusto Graphics/idle.png");
        map.put(Texture.PLAYER_JUMP_FALL, "./Gugusto Graphics/jumpfall.png");
        map.put(Texture.PLAYER_WALK, "./Gugusto Graphics/walk.png");

        map.put(Texture.ICONS, "./Gugusto Graphics/icons.png");
        map.put(Texture.BUTTON_SECTIONS, "./Gugusto Graphics/button_sections.png");

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
