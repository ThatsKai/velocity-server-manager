package it.thatskai.servermanager.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import it.thatskai.servermanager.VelocityPlugin;
import it.thatskai.servermanager.utils.Cache;
import it.thatskai.servermanager.utils.ChatUtils;

import java.util.Optional;

public class MainCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if(!source.hasPermission("smanager.admin")){
            source.sendMessage(ChatUtils.color(Cache.NO_PERMISSIONS));
            return;
        }

        if(args.length >= 1){
            String sub = args[0];
            if(sub.equalsIgnoreCase("reload")){
                VelocityPlugin.getInstance().reload(true);
                source.sendMessage(ChatUtils.color("&aServerManager has been reloaded."));
                return;
            }
            if(sub.equalsIgnoreCase("list")){
                source.sendMessage(ChatUtils.color("&cServerManager server list:"));
                for(String serverName : VelocityPlugin.getInstance().getServerManager().getServers()){
                    Optional<RegisteredServer> registeredServer = VelocityPlugin.getInstance().getProxyServer().getServer(serverName);
                    int port = VelocityPlugin.getInstance().getConfigManager().getDatabase().getInt("servers."+serverName+".port");
                    source.sendMessage(ChatUtils.color("&c - "+serverName+": "+(registeredServer.isPresent() ? "&aLOADED" : "&cUNLOADED")+"&c / Port: "+port));
                }
                return;
            }

            if(args.length >= 2){
                String server = args[1];
                if(args.length >= 4 && sub.equalsIgnoreCase("add")){
                    String host = args[2];
                    int port = Integer.parseInt(args[3]);
                    VelocityPlugin.getInstance().getServerManager().add(server, host, port);
                    VelocityPlugin.getInstance().getServerManager().loadSpecificServer(server);
                    source.sendMessage(ChatUtils.color("&eServer "+server+" added with port "+port+" and host "+host));
                    return;
                }
                if(sub.equalsIgnoreCase("remove")){
                    VelocityPlugin.getInstance().getServerManager().unLoadSpecificServer(server);
                    VelocityPlugin.getInstance().getServerManager().remove(server);
                    source.sendMessage(ChatUtils.color("&eServer "+server+" removed"));
                    return;
                }
                if(sub.equalsIgnoreCase("info")){
                    if(VelocityPlugin.getInstance().getConfigManager().getDatabase().get("servers."+server) == null){
                        source.sendMessage(ChatUtils.color("&cThis server does not exist."));
                        return;
                    }
                    String host = VelocityPlugin.getInstance().getConfigManager().getDatabase().getString("servers."+server+".host");
                    String port = VelocityPlugin.getInstance().getConfigManager().getDatabase().getString("servers."+server+".port");
                    source.sendMessage(ChatUtils.color("&c"+server+" info:"));
                    source.sendMessage(ChatUtils.color("&c Host: "+host));
                    source.sendMessage(ChatUtils.color("&c Port: "+port));
                    return;
                }
            }
        }

        source.sendMessage(ChatUtils.color("&c/sm reload"));
        source.sendMessage(ChatUtils.color("&c/sm list"));
        source.sendMessage(ChatUtils.color("&c/sm add <server-name> <server-host> <server-port>"));
        source.sendMessage(ChatUtils.color("&c/sm remove <server-name>"));
        source.sendMessage(ChatUtils.color("&c/sm info <server-name>"));
    }
}
