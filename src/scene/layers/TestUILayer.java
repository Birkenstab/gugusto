package scene.layers;

import scene.UILayer;
import ui.Button;
import util.Size;
import util.Vector;

public class TestUILayer extends UILayer {

    public TestUILayer(){
        addComponent(new Button(new Vector(50, 100), new Size(100, 50)));
        addComponent(new Button(new Vector(100, 100), new Size(100, 50)));
    }

}
