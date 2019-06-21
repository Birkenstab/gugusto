package de.thu.gpro.gugusto.game.level.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class LevelUtil {

    public static final Path LEVEL_DIR = Paths.get("./levels/");
    private static final Path LEVELS_CFG = Paths.get(LEVEL_DIR + "/levels.cfg");

    private LevelUtil(){}

    public static List<Path> getAllLevels(Path levelDirectory){
        try {
            Stream<Path> stream = Files.find(levelDirectory, 3, (path, bfa) -> path.toString().endsWith(".gug")
                                                                                                && bfa.isRegularFile());
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Path> getPlayableLevels(){
        try {
            byte[] bytes = Files.readAllBytes(LEVELS_CFG);
            return getFileNames(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Path> getFileNames(String content){
        List<Path> filePaths = new ArrayList<>();
        content = content.replaceAll("\\r\\n", "\n");
        content = content.replaceAll("\\r", "\n");
        String[] fileNames = content.split("\n");

        for(String fileName : fileNames) filePaths.add(Path.of("./levels/" + fileName + ".gug"));

        return filePaths;
    }

}
