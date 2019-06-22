package de.thu.gpro.gugusto.game.level.io;

import de.thu.gpro.gugusto.game.level.Profile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ProfileLoader {

    private ProfileLoader(){}

    public static final Path PROFILE_PATH = Paths.get("./PROFILE");

    public static void save(Profile profile){
        save(profile, PROFILE_PATH);
    }

    public static void save(Profile profile, Path path){
        byte[] bytes = ProfileFormat.encode(profile);
        IOUtil.write(bytes, path);
    }

    public static Profile load(){
        return load(PROFILE_PATH);
    }

    public static Profile load(Path path){
        if(!new File(path.toUri()).exists()){
            Profile profile = new Profile();
            save(profile, path);
            return profile;
        }

        byte[] bytes = IOUtil.read(path);
        return ProfileFormat.decode(bytes);
    }

}
