package ui.components;

import util.Size;
import util.Vector;

public class Container extends UIComponent {

    public Container(Vector position, Size size) {
        super(position, size);
    }

    public void setPosition(Vector position){
        Vector offset = position.clone().subtract(boundingBox.getPosition());
        boundingBox.getPosition().set(position);

        for(UIComponent component : components) component.getBoundingBox().getPosition().add(offset);
    }

}
