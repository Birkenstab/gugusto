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

        map.put(Texture.BLOCK_GRASS,  "/textures/blocks/grass.png");
        map.put(Texture.BLOCK_COIN, "/textures/blocks/coin.png");
        map.put(Texture.BLOCK_CHEST, "/textures/blocks/chest.png");
        map.put(Texture.BLOCK_JUMP_PAD, "/textures/blocks/jumpPad.png"); // TODO
        map.put(Texture.BLOCK_DIRT, "/textures/blocks/dirt.png");
        map.put(Texture.BLOCK_GOAL, "/textures/blocks/goal.png");

        map.put(Texture.ENEMY_SAW, "/textures/enemies/saw.png");
        map.put(Texture.ENEMY_GUSTAV_WALK, "/textures/enemies/gustav.png");
        map.put(Texture.ENEMY_VERFOLGI_WALK, "/textures/enemies/verfolgi.png");

        map.put(Texture.PLAYER_IDLE, "/textures/player/playerBlink.png");
        map.put(Texture.PLAYER_JUMP_FALL, "/textures/player/playerJump.png");
        map.put(Texture.PLAYER_WALK, "/textures/player/playerRun.png");

        map.put(Texture.ICONS, "/textures/miscellaneous/icons.png");
        map.put(Texture.BUTTON_SECTIONS, "/textures/miscellaneous/buttonSections.png");
        map.put(Texture.BACKGROUND, "/textures/miscellaneous/background.png");

        for (Map.Entry<Texture, String> entry : map.entrySet()) {
            try {
                textures.put(entry.getKey(), ImageIO.read(TextureLoader.class.getResource(entry.getValue())));
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("Error while reading file " + entry.getValue());
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
