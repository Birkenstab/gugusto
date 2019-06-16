package scene.scenes.mapeditor;

import game.object.GameObject;
import game.object.blocks.BlockType;
import game.object.blocks.Coin;
import game.object.blocks.GrassBlock;
import game.object.enemies.Enemy;
import game.object.enemies.EnemyType;
import game.object.enemies.Saw;
import graphic.Texture;
import graphic.TextureLoader;
import input.event.EventCallback;
import input.event.InputEventType;
import input.event.KeyEvent;
import ui.components.Panel;
import ui.components.ScrollPanel;
import ui.components.TabPanel;
import ui.components.button.ImageButton;
import ui.components.button.TextButton;
import ui.components.button.TextButtonFactory;
import util.Size;
import util.Vector;

import java.awt.*;
import java.util.EnumSet;

public class ObjectPlacementSelector extends TabPanel {

    private MapEditorAction mapEditorAction;

    public ObjectPlacementSelector(Vector position, MapEditorAction mapEditorAction) {
        super(position, 200);

        this.mapEditorAction = mapEditorAction;

        TextButtonFactory tbf = new TextButtonFactory(100, new Vector(0, 6), 16);
        TextButton tab1 = tbf.create(new Vector(), "Blocks");
        TextButton tab2 = tbf.create(new Vector(100, 0), "Enemys");
        Panel header = new Panel(new Vector(), new Size(200, 30), Color.BLACK);

        tab1.setClickListener(b -> setTab(0));
        tab2.setClickListener(b -> setTab(1));
        header.addUIComponent(tab1);
        header.addUIComponent(tab2);

        setHeader(header);
        addPage(buildBlockSelection());
        addPage(buildEnemySelection());
        build();

        setVisible(false);

        addListener(InputEventType.KEY_DOWN, (EventCallback<KeyEvent>) e -> {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE && isVisible()){
                setVisible(false);
                return true;
            }
            return false;
        });
    }

    private ScrollPanel buildBlockSelection(){
        ScrollPanel scrollPanel = new ScrollPanel(new Vector(), new Size(200, 200), 2);

        EnumSet<BlockType> types = EnumSet.complementOf(EnumSet.of(BlockType.NONE)); // Alle außer NONE
        int i = 0;
        for (BlockType blockType : types) {
            ImageButton button = new ImageButton(new Vector((i % 4) * 50, (i / 4) * 50), new Size(32, 32), MapEditorObjectIconProvider.getBlockIcon(blockType));
            button.setClickListener(b -> setSelection(blockType.getId(), Type.Block));
            scrollPanel.addUIComponent(button);
            i++;
        }

        scrollPanel.build();

        return scrollPanel;
    }

    private ScrollPanel buildEnemySelection(){
        ScrollPanel scrollPanel = new ScrollPanel(new Vector(), new Size(200, 200), 2);

        EnumSet<EnemyType> types = EnumSet.complementOf(EnumSet.of(EnemyType.NONE)); // Alle außer NONE
        int i = 0;
        for (EnemyType enemyType : types) {
            ImageButton button = new ImageButton(new Vector((i % 4) * 50, (i / 4) * 50), new Size(32, 32), MapEditorObjectIconProvider.getEnemyIcon(enemyType));
            button.setClickListener(b -> setSelection(enemyType.getId(), Type.Enemy));
            scrollPanel.addUIComponent(button);
            i++;
        }

        scrollPanel.build();

        return scrollPanel;
    }

    private boolean setSelection(int id, GameObject.Type type){
        mapEditorAction.setSelectedObject(id, type);
        setVisible(false);
        return true;
    }

}
