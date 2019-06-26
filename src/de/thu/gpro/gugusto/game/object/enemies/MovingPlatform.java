package de.thu.gpro.gugusto.game.object.enemies;

import de.thu.gpro.gugusto.game.Camera;
import de.thu.gpro.gugusto.game.object.DynamicGameObject;
import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.game.object.player.Player;
import de.thu.gpro.gugusto.graphic.Texture;
import de.thu.gpro.gugusto.graphic.TextureLoader;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MovingPlatform extends Enemy {

    public static final BufferedImage TEXTURE = TextureLoader.get(Texture.BLOCK_GRASS);

    private Vector originPosition;
    private List<DynamicGameObject> attached;
    private int range = 5;
    private double offset = 0;
    private int speed = 3;

    public MovingPlatform(Vector position){
        this(position, 3);
    }

    public MovingPlatform(Vector position, int width) {
        super(EnemyType.MOVING_PLATFORM, position, new Size(width, 1));
        originPosition = position.clone();
        attached = new ArrayList<>();
        setSolid(true);
        setMovable(false);
    }

    @Override
    public void update(double delta){
        super.update(delta);

        double increment = speed * delta;
        double newOffset = offset + increment;

        if(newOffset >= range){
            increment = range - newOffset;
            speed *= -1;
        } else if(newOffset <= 0){
            increment = -newOffset;
            speed *= -1;
        }

        offset += increment;

        getBoundingBox().getPosition().set(new Vector(offset, 0).add(originPosition));

        if(attached.size() > 0){
            Vector offsetVector = new Vector(increment, 0);
            for(DynamicGameObject object : attached){
                object.getBoundingBox().getPosition().add(offsetVector);
            }

            attached = new ArrayList<>();
        }
    }

    @Override
    public void draw(Graphics2D g2d, Camera camera){
        super.draw(g2d, camera);

        g2d.drawImage(TEXTURE, getX(), getY(), getWidth(), getHeight(), null);
    }

    @Override
    public void collision(GameObject object){
        super.collision(object);

        if(object instanceof DynamicGameObject){
            attached.add((DynamicGameObject)object);
        }
    }

}
