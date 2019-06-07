package ui.components;

import input.event.MouseEvent;
import util.Size;
import util.Vector;

public abstract class Button extends UIComponent {

    private OnClickListener clickListener;
    protected boolean hover = false;

    public Button(Vector position, Size size) {
        super(position, size);
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

    @Override
    public boolean onMouseMove(MouseEvent e){
        return true;
    }

    @Override
    public boolean onMouseClick(MouseEvent e){
        if(clickListener != null) {
            clickListener.onClick(this);
            return true;
        }

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
