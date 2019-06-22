package de.thu.gpro.gugusto.game.level.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

final class IOUtil {

    private IOUtil(){}

    static void write(byte[] data, Path path){
        try {
            Files.write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static byte[] read(Path path){
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File not found: " + path);

        return null;
    }

}
