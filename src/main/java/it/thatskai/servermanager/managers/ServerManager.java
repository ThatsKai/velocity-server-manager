package it.thatskai.servermanager.managers;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import dev.dejvokep.boostedyaml.YamlDocument;
import it.thatskai.servermanager.VelocityPlugin;
import it.thatskai.servermanager.utils.ChatUtils;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServerManager {
    private final YamlDocument database = VelocityPlugin.getInstance().getConfigManager().getDatabase();

    public List<String> getServers(){
        List<String> list = new ArrayList<>();
        if(database.get("servers") == null) return new ArrayList<>();
        for(Object object : database.getSection("servers").getStoredValue().keySet()){
            list.add(String.valueOf(object));
        }
        return list;
    }

    @SneakyThrows
    public void add(String name, String host, int port){
        if(database.get("servers."+name) != null) return;
        database.set("servers."+name+".host", host);
        database.set("servers."+name+".port", port);
        VelocityPlugin.getInstance().getConfigManager().getDatabase().save();
    }

    @SneakyThrows
    public void remove(String name){
        database.set("servers."+name, null);
        VelocityPlugin.getInstance().getConfigManager().getDatabase().save();
    }

    public void loadServers(){
        if(database.get("servers") == null) return;
        for(Object object : database.getSection("servers").getStoredValue().keySet()){
            String name = String.valueOf(object);
            String host = database.getString("servers."+name+".host");
            int port = database.getInt("servers."+name+".port");
            if(host == null || port == 0) continue;
            register(name, host, port);
        }
    }
    public void unLoadServers(){
        for(Object object : database.getSection("servers").getStoredValue().keySet()){
            String name = String.valueOf(object);
            Optional<RegisteredServer> server = VelocityPlugin.getInstance().getProxyServer().getServer(name);
            server.ifPresent(this::unRegister);
        }
    }

    public boolean loadSpecificServer(String name){
        if(database.get("servers."+name) == null) return false;
        String host = database.getString("servers."+name+".host");
        int port = database.getInt("servers."+name+".port");
        if(host == null || port == 0) return false;
        return register(name, host, port);
    }

    public boolean unLoadSpecificServer(String name){
        if(database.get("servers."+name) == null) return false;
        Optional<RegisteredServer> server = VelocityPlugin.getInstance().getProxyServer().getServer(name);
        if(server.isPresent()){
            unRegister(server.get());
            return true;
        }
        return false;
    }

    public void unRegister(RegisteredServer server){
        VelocityPlugin.getInstance().getProxyServer().unregisterServer(server.getServerInfo());
        ChatUtils.log("Server "+server.getServerInfo().getName()+" has been unloaded");
    }

    public boolean register(String name, String host, int port){
        if(VelocityPlugin.getInstance().getProxyServer().getServer(name).isPresent()) return false;
        RegisteredServer server = VelocityPlugin.getInstance().getProxyServer().registerServer(new ServerInfo(name, new InetSocketAddress(host, port)));
        server.ping();
        ChatUtils.log("Server "+name+" has been loaded");
        return true;
    }
}
