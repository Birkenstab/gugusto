package ui.components.button;

import input.event.InputEventType;
import input.event.MouseEvent;
import ui.components.UIComponent;
import util.Size;
import util.Vector;

public abstract class Button extends UIComponent {

    private OnClickListener clickListener;
    protected boolean hover = false;

    public Button(Vector position, Size size) {
        super(position, size);

        addListener(InputEventType.MOUSE_MOVE, e -> true);
        addListener(InputEventType.MOUSE_DOWN, this::onMouseClick);
    }

    public void click(){
        if(clickListener != null) clickListener.onClick(this);
    }

    @Override
    public void onMouseEnter(MouseEvent e){
        setHover(true);
    }

    @Override
    public void onMouseLeave(MouseEvent e){
        setHover(false);
    }

    private boolean onMouseClick(MouseEvent e){
        click();
        return clickListener != null;
    }

    public void setHover(boolean hover){
        this.hover = hover;
    }
    public void setClickListener(OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface OnClickListener {

        void onClick(Button button);

    }

}
