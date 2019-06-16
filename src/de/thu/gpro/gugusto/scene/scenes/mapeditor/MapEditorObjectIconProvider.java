package de.thu.gpro.gugusto.scene.scenes.mapeditor;

import de.thu.gpro.gugusto.game.object.blocks.BlockType;
import de.thu.gpro.gugusto.game.object.enemies.EnemyType;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;

import java.awt.image.BufferedImage;
import java.util.Map;

public class MapEditorObjectIconProvider {
    private static Map<EnemyType, Texture> enemyMap = Map.of(
            EnemyType.SAW, Texture.ENEMY_SAW,
            EnemyType.GUSTAV, Texture.BUTTON_SECTIONS // TODO
    );

    private static Map<BlockType, Texture> blockMap = Map.of(
            BlockType.GRASS, Texture.BLOCK_GRASS,
            BlockType.GOAL, Texture.BUTTON_SECTIONS, // TODO
            BlockType.COIN, Texture.BLOCK_COIN // TODO
    );

    public static BufferedImage getEnemyIcon(EnemyType type) {
        return TextureLoader.get(enemyMap.get(type));
    }

    public static BufferedImage getBlockIcon(BlockType type) {
        return TextureLoader.get(blockMap.get(type));
    }
}
