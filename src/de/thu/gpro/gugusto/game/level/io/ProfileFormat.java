package de.thu.gpro.gugusto.game.level.io;

import de.thu.gpro.gugusto.game.level.Profile;

public final class ProfileFormat {

    private ProfileFormat(){}

    public static byte[] encode(Profile profile){
        int size = getRequiredFileSize(profile);
        DataView dataView = new DataView(size);

        dataView.writeUint32(profile.getLastUnlockedLevelId());

        return dataView.getByteArray();
    }

    public static Profile decode(byte[] bytes){
        DataView dataView = new DataView(bytes);

        int lastUnlockedLevelId = dataView.readUint32();

        return new Profile(lastUnlockedLevelId);
    }

    private static int getRequiredFileSize(Profile profile){
        int size = 0;

        size += 4; //last unlocked level

        return size;
    }

}
