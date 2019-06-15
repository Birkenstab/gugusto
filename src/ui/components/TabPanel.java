package ui.components;

import collision.BoundingBox;
import util.Size;
import util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TabPanel extends Panel {

    private UIComponent header;
    private List<UIComponent> pages;

    private int tabIndex = -1;

    /*
    * The header UIComponent will be moved to `position`.
    * The page size will be: new Size(header.getBoundingBox().getSize().getWidth(), height);
    * The TabPanel size will be: header.getBoundingBox().getSize().add(new Size(0, height));
    * */
    public TabPanel(Vector position, int height) {
        super(position, new Size(0, height), Color.CYAN);

        pages = new ArrayList<>();
    }

    public void build(){
        if(header == null) throw new Error("Can't build TabPanel without a header component.");
        if(pages.size() < 2) throw new Error("TabPanel needs at least two pages.");

        updatePositionAndSizes();
        addUIComponent(header);
        addUIComponent(pages);

        if(tabIndex == -1) setTab(0);
    }

    private void updatePositionAndSizes(){
        BoundingBox hbb = header.getBoundingBox();
        Vector pagePosition = boundingBox.getPosition().clone().add(new Vector(0, hbb.getSize().getHeight()));
        Size pageSize = new Size(header.getBoundingBox().getSize().getWidth(), boundingBox.getSize().getHeight());

        header.setPosition(boundingBox.getPosition());
        boundingBox.getSize().set(pageSize.getWidth(), pageSize.getHeight() + hbb.getSize().getHeight());

        for(UIComponent page : pages){
            page.setPosition(pagePosition);
            page.getBoundingBox().getSize().set(pageSize);
            page.setVisible(false);
        }
    }

    public void setHeader(UIComponent component){
        header = component;
    }

    public void addPage(UIComponent page){
        pages.add(page);
    }

    public void setTab(int index){
        if(index < pages.size() && index > -1){
            if(tabIndex != -1) pages.get(tabIndex).setVisible(false);
            pages.get(index).setVisible(true);
            tabIndex = index;
        } else {
            throw new Error(String.format("Invalid tab index %d. Only %d pages are in the panel.", index, pages.size()));
        }
    }

    @Override
    public void setVisible(boolean visible){
        this.visible = visible;

        if(header != null) header.setVisible(visible);
        if(tabIndex != -1) pages.get(tabIndex).setVisible(visible);
    }

}
