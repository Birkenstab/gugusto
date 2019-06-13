package scene.scenes.mapeditor;

import game.object.GameObject;
import game.object.blocks.BlockType;
import game.object.blocks.Coin;
import game.object.blocks.GrassBlock;
import game.object.enemies.EnemyType;
import game.object.enemies.Saw;
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
        ImageButton grassButton = new ImageButton(new Vector(), new Size(32, 32), GrassBlock.texture);
        ImageButton coinButton = new ImageButton(new Vector(50, 0), new Size(32, 32), Coin.texture.getSubimage(0, 0, 128, 128));

        grassButton.setClickListener(b -> setSelection(BlockType.GRASS.getId(), Type.Block));
        coinButton.setClickListener(b -> setSelection(BlockType.COIN.getId(), Type.Block));

        ScrollPanel scrollPanel = new ScrollPanel(new Vector(), new Size(200, 200), 2);
        scrollPanel.addUIComponent(grassButton);
        scrollPanel.addUIComponent(coinButton);
        scrollPanel.build();

        return scrollPanel;
    }

    private ScrollPanel buildEnemySelection(){
        ImageButton sawButton = new ImageButton(new Vector(), new Size(32, 32), Saw.texture);

        sawButton.setClickListener(b -> setSelection(EnemyType.SAW.getId(), Type.Enemy));

        ScrollPanel scrollPanel = new ScrollPanel(new Vector(), new Size(200, 200), 2);
        scrollPanel.addUIComponent(sawButton);
        scrollPanel.build();

        return scrollPanel;
    }

    private boolean setSelection(int id, GameObject.Type type){
        mapEditorAction.setSelectedObject(id, type);
        setVisible(false);
        return true;
    }

}
