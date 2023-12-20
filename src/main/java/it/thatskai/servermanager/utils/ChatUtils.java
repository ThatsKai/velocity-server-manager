package it.thatskai.servermanager.utils;

import it.thatskai.servermanager.VelocityPlugin;
import net.kyori.adventure.text.Component;

public class ChatUtils {

    public static Component color(String s){
        return Component.text(s.replace("&","§"));
    }

    public static void log(String s){
        VelocityPlugin.getInstance().getLogger().info(s);
    }
}
