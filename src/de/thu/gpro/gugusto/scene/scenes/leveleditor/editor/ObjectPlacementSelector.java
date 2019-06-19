package de.thu.gpro.gugusto.scene.scenes.leveleditor.editor;

import de.thu.gpro.gugusto.game.object.GameObject;
import de.thu.gpro.gugusto.game.object.enemies.EnemyType;
import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.ui.components.ScrollPanel;
import de.thu.gpro.gugusto.ui.components.TabPanel;
import de.thu.gpro.gugusto.ui.components.button.ImageButton;
import de.thu.gpro.gugusto.ui.components.button.TextButton;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;
import de.thu.gpro.gugusto.game.object.blocks.BlockType;
import de.thu.gpro.gugusto.ui.components.Panel;

import java.awt.*;
import java.util.EnumSet;

public class ObjectPlacementSelector extends TabPanel {

    private LevelEditorAction levelEditorAction;

    public ObjectPlacementSelector(Vector position, LevelEditorAction levelEditorAction) {
        super(position, 200);

        this.levelEditorAction = levelEditorAction;

        LabelFactory lf = new LabelFactory(100, new Vector(0, 6), 16);
        TextButton tab1 = new TextButton(lf.create(new Vector(), "Blocks"));
        TextButton tab2 = new TextButton(lf.create(new Vector(100, 0), "Enemys"));
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
            ImageButton button = new ImageButton(new Vector((i % 4) * 50, (i / 4) * 50), new Size(32, 32), LevelEditorObjectIconProvider.getBlockIcon(blockType));
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
            ImageButton button = new ImageButton(new Vector((i % 4) * 50, (i / 4) * 50), new Size(32, 32), LevelEditorObjectIconProvider.getEnemyIcon(enemyType));
            button.setClickListener(b -> setSelection(enemyType.getId(), Type.Enemy));
            scrollPanel.addUIComponent(button);
            i++;
        }

        scrollPanel.build();

        return scrollPanel;
    }

    private boolean setSelection(int id, GameObject.Type type){
        levelEditorAction.setSelectedObject(id, type);
        setVisible(false);
        return true;
    }

}
