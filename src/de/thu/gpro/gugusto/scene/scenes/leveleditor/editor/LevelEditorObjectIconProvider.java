package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.object.blocks.*;
import de.thu.gpro.gugusto.game.object.enemies.EnemyType;
import de.thu.gpro.gugusto.game.object.enemies.Gustav;
import de.thu.gpro.gugusto.game.object.enemies.MovingPlatform;
import de.thu.gpro.gugusto.game.object.enemies.Verfolgi;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

class LevelEditorObjectIconProvider {
    private static Map<EnemyType, BufferedImage> enemyMap = new HashMap<>();
    private static Map<BlockType, BufferedImage> blockMap = new HashMap<>();

    static {
        enemyMap.put(EnemyType.SAW, TextureLoader.get(Texture.ENEMY_SAW));
        enemyMap.put(EnemyType.GUSTAV, Gustav.TEXTURE);
        enemyMap.put(EnemyType.VERFOLGI, Verfolgi.TEXTURE);
        enemyMap.put(EnemyType.MOVING_PLATFORM, MovingPlatform.TEXTURE);

        blockMap.put(BlockType.GRASS, GrassBlock.TEXTURE);
        blockMap.put(BlockType.GOAL, TextureLoader.get(Texture.BUTTON_SECTIONS));
        blockMap.put(BlockType.COIN, Coin.TEXTURE);
        blockMap.put(BlockType.CHEST, Chest.TEXTURE);
        blockMap.put(BlockType.JUMP_PAD, JumpPad.TEXTURE);
    }

    public static BufferedImage getEnemyIcon(EnemyType type) {
        return enemyMap.get(type);
    }

    public static BufferedImage getBlockIcon(BlockType type) {
        return blockMap.get(type);
    }
}
