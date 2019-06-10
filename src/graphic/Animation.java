package graphic;


import java.awt.*;
import java.io.File;

public class Animation {

    private static File[] rechts = new File[16];
    private static File[] links = new File[16];
    private static char previous_key = '0';
    private static int index =0;

    public Animation() {
        init();
    }


   private void init() {
       for (int i = 1; i <=16 ; i++) {
           rechts[i-1] =  new File("./Gugusto Graphics/A_run/run"+i+".png");
           links[i-1] = new File(("./Gugusto Graphics/A_backwards/backwards"+(17-i)+".png"));
       }

   }
   public File update(char key){
        switch (key) {
            case 'd' :
                if (previous_key == 'd' && index < 16) { return rechts[index++]; }
                else index = 0; previous_key = 'd'; return rechts[index];

            case 'a' :

                if (previous_key == 'a' && index < 16) { return links[index++]; }
                else index = 0; previous_key = 'a'; return links[index];



            default:
                return new File("./Gugusto Graphics/char_tmp.png");

        }
   }
}
