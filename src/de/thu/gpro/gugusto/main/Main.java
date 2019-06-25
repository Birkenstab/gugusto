package de.thu.gpro.gugusto.main;

import de.thu.gpro.gugusto.game.Game;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args){
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/fonts/Mali-Bold.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // Wenn level Ordner noch nicht existiert, dann aus jar rauskopieren
        File f = new File("./levels/levels.cfg");
        if (!f.exists()) {
            File folder = new File("./levels");
            if (!folder.exists()) {
                folder.mkdir();
            }
            try {
                URI uri = Main.class.getResource("/levels").toURI();
                System.out.println(uri);
                Path myPath;
                if (uri.getScheme().equals("jar")) {
                    FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                    myPath = fileSystem.getPath("/levels");
                } else {
                    myPath = Paths.get(uri);
                }
                Stream<Path> walk = Files.walk(myPath, 1);
                for (Iterator<Path> it = walk.iterator(); it.hasNext();){
                    Path path = it.next();
                    if (path.getFileName().toString().equals("levels"))
                        continue;
                    Files.copy(path,
                            folder.toPath().resolve(path.getFileName().toString()));
                }
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }

        Game.getInstance().run();
    }

}
