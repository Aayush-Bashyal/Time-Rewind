package net.bikash.timerewind.rewind;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;


import net.fabricmc.loader.api.FabricLoader;


public class RewindConfig {

  


    private static final Path CONFIG_FILE = FabricLoader.getInstance()
    .getConfigDir().resolve("time-rewind.txt") ;

    private static int rewindSeconds = 5;

    public static int getRewindSeconds(){
        return rewindSeconds;
    }

    public static void setRewindSeconds(int seconds){
        rewindSeconds = Math.max(0, Math.min(15, seconds));
        save();
    }

    public static void load(){
        if(!Files.exists(CONFIG_FILE)){
            save();
            return;
        }

        try {
            String value = Files.readString(CONFIG_FILE).trim();
            rewindSeconds = Math.max(0, Math.min(15, Integer.parseInt(value)) );

        } catch(Exception exception){
            System.err.println("Could not load time rewind stttings");
            exception.printStackTrace();
        }
}

private static void save(){
    try{
        Files.createDirectories(CONFIG_FILE.getParent());
        Files.writeString(CONFIG_FILE, String.valueOf(rewindSeconds));
    
    } catch (IOException exception){
        System.err.println("Could not save Time Rewind settings");
        exception.printStackTrace();
    }
}
}
;