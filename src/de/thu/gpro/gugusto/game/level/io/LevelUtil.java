package de.thu.gpro.gugusto.game.level.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class LevelUtil {

    private LevelUtil(){}

    public static List<Path> getAllLevels(Path levelDirectory) throws IOException {
        Stream<Path> stream = Files.find(levelDirectory, 3, (path, bfa) -> path.toString().endsWith(".gug")
                                                                                        && bfa.isRegularFile());
        return stream.collect(Collectors.toList());
    }

}
