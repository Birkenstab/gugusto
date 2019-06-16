package de.thu.gpro.gugusto.ui.components.button;

import de.thu.gpro.gugusto.input.event.InputEventType;
import de.thu.gpro.gugusto.input.event.MouseEvent;
import de.thu.gpro.gugusto.ui.components.UIComponent;
import de.thu.gpro.gugusto.util.Size;
import de.thu.gpro.gugusto.util.Vector;

public abstract class Button extends UIComponent {

    private OnClickListener clickListener;
    protected boolean hover = false;

    public Button(Vector position, Size size) {
        super(position, size);

        addListener(InputEventType.MOUSE_MOVE, e -> true);
        addListener(InputEventType.MOUSE_DOWN, this::onMouseClick);
    }

    public boolean click(){
        if(clickListener != null) clickListener.onClick(this);
        return clickListener != null;
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
        if(e.getButton() == MouseEvent.BUTTON1) return click();
        return false;
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
