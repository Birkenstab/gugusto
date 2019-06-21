package de.thu.gpro.gugusto.ui.components.button;

import de.thu.gpro.gugusto.game.Game;
import de.thu.gpro.gugusto.input.event.EventCallback;
import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.KeyEvent;
import de.thu.gpro.gugusto.scene.scenes.startmenu.StartMenuScene;
import de.thu.gpro.gugusto.ui.icon.Icon;
import de.thu.gpro.gugusto.util.Vector;

public class BackToStartButton extends IconButton {

    private static final Vector position = new Vector(10, 10);

    public BackToStartButton (){
        super(position, Icon.ARROW_LEFT);

        setClickListener(b -> backToStartMenu());
        addListener(InputEventType.KEY_DOWN, (EventCallback<KeyEvent>) e -> {
            if(shouldGoBack(e)) return backToStartMenu();
            return false;
        });
    }

    protected boolean shouldGoBack(KeyEvent e){
        return e.getKeyCode() == KeyEvent.VK_ESCAPE;
    }

    private boolean backToStartMenu(){
        Game.getInstance().getSceneManager().setScene(new StartMenuScene());
        return true;
    }

}
