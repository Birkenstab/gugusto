package de.thu.gpro.gugusto.scene.scenes.leveleditor.selection;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.game.level.io.LevelUtil;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.scene.scenes.leveleditor.editor.LevelEditorScene;
import de.thu.gpro.gugusto.ui.components.Label;
import de.thu.gpro.gugusto.ui.components.LabelFactory;
import de.thu.gpro.gugusto.ui.components.Panel;
import de.thu.gpro.gugusto.ui.components.ScrollPanel;
import de.thu.gpro.gugusto.ui.components.button.IconButton;
import de.thu.gpro.gugusto.ui.components.button.Button;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LevelEditorSelectionLevelList extends ScrollPanel {

    private static final Size size = new Size(600, 530);
    private static final Vector position = new Vector((Game.INNER_WIDTH - size.getWidth()) / 2, 120);
    private static final Font font = new Font("Arial", Font.BOLD, 16);

    private ConfirmationPopup confirmationPopup;
    private ListItem toDeleteListItem;
    private Button.OnClickListener callback;
    private int listItemCount;

    public LevelEditorSelectionLevelList(ConfirmationPopup confirmationPopup){
        super(position, size, 3);

        this.confirmationPopup = confirmationPopup;
        color = Color.GRAY;
        callback = button -> deleteListItem();
        color = new Color(0, 0, 0, 50);

        buildList();
        build();
    }

    private void buildList(){
        List<Path> levelPathList = LevelUtil.getAllLevels(LevelUtil.LEVEL_DIR);
        listItemCount = levelPathList.size();

        for(int i = 0; i < levelPathList.size(); i++){
            addUIComponent(new ListItem(i, levelPathList.get(i)));
        }
    }

    private void editLevel(Path path){
        Game.getInstance().getSceneManager().setScene(new LevelEditorScene(path));
    }

    private void deleteLevel(Path path, ListItem item){
        confirmationPopup.prompt(path);
        toDeleteListItem = item;
    }

    private void deleteListItem(){
        int index = components.indexOf(toDeleteListItem);

        for(int i = index; i < listItemCount - 1; i++){
            ((ListItem)components.get(i)).replace((ListItem)components.get(i + 1));
        }

        components.get(listItemCount - 1).setVisible(false);
        listItemCount--;
        maxScrollY -= ListItem.height;
    }

    private class ListItem extends Panel {

        public static final int height = 50;
        private final Size iconSize = new Size(38, 38);
        private final Color color = new Color(230, 230, 230);

        private Path path;
        private Label levelName;
        private IconButton editLevel;
        private IconButton deleteLevel;

        public ListItem(int n, Path path){
            super(new Vector(0, n * height).add(position), new Size(size.getWidth(), height), new Color(230, 230, 230));
            this.path = path;
            super.color = color;

            setFilter(InputEventType.MOUSE_SCROLL, false);

            build();
        }

        private void build(){
            LabelFactory lf = new LabelFactory(new Vector(0, 0), font);
            String name = path.getFileName().toString().split("\\.")[0];
            Vector position = boundingBox.getPosition();
            Vector levelNamePosition = new Vector(20, 16).add(position);
            Vector editLevelPosition = new Vector(size.getWidth() - 110, 6).add(position);
            Vector deleteLevelPosition = new Vector(iconSize.getWidth() + 6, 0).add(editLevelPosition);

            levelName = lf.create(levelNamePosition, name);
            editLevel = new IconButton(editLevelPosition, iconSize, Icon.SETTINGS);
            deleteLevel = new IconButton(deleteLevelPosition, iconSize, Icon.REMOVE);

            editLevel.setClickListener(b -> LevelEditorSelectionLevelList.this.editLevel(path));
            deleteLevel.setClickListener(b -> LevelEditorSelectionLevelList.this.deleteLevel(path, this));

            addUIComponent(levelName);
            addUIComponent(editLevel);
            addUIComponent(deleteLevel);
        }

        public void replace(ListItem item){
            path = item.path;
            levelName.setText(item.levelName.getText());
        }

        @Override
        public void draw(Graphics2D g2d){
            super.draw(g2d);

            g2d.setColor(Color.BLACK);
            g2d.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth(), getY() + getHeight() - 1);
        }

    }

    public Button.OnClickListener getCallback(){
        return callback;
    }

}
