package it.thatskai.servermanager.utils;

import dev.dejvokep.boostedyaml.YamlDocument;
import it.thatskai.servermanager.VelocityPlugin;

public class Cache {

    public static String NO_PERMISSIONS;

    public static void load(){
        YamlDocument config = VelocityPlugin.getInstance().getConfigManager().getConfig();
        NO_PERMISSIONS = config.getString("messages.no-permission");
    }
}
