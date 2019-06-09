package graphic;


import java.awt.*;
import java.io.File;

public class Animation {


    private String path;
    private int length;
    private char key;
    private File[] gallery;
    private int index =0;
    private int counter;

    public Animation(String path, int length, boolean delayed, char key) {
        this.path = path;
        this.length = length;
        this.key=key;
        this.gallery = new File[length];
        init();
    }


    private void init() {
        for (int i = 1; i <=length ; i++) {
            gallery[i-1] =  new File(path+i+".png");
        }
    }


    public File update(char prev){

        if (prev == key && index < length) {  return gallery[index++]; }
        else  index = 0; return gallery[index];

    }

    public File update(char prev,int delay){

        if(counter == delay ){
            if (index < length) {
                return gallery[index++];
            } else index = 0; counter=0;return gallery[index];
        }
        else counter++; return gallery[index];

    }
}
