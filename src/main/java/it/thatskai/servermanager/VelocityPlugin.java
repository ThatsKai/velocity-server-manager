package it.thatskai.servermanager;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.dejvokep.boostedyaml.YamlDocument;
import it.thatskai.servermanager.commands.MainCommand;
import it.thatskai.servermanager.managers.ConfigManager;
import it.thatskai.servermanager.managers.ServerManager;
import it.thatskai.servermanager.utils.Cache;
import lombok.Getter;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "velocity-server-manager",
        name = "velocity-server-manager",
        version = "1.0"
)
public class VelocityPlugin {

    @Getter
    @Inject
    private final Logger logger;

    @Getter
    private final ProxyServer proxyServer;

    @Getter
    private ConfigManager configManager;

    @Getter
    private ServerManager serverManager;

    @Getter
    private final Path directory;

    @Getter
    private static VelocityPlugin instance;

    @Inject
    public VelocityPlugin(Logger logger, ProxyServer proxyServer, @DataDirectory Path directory) {
        this.logger = logger;
        this.proxyServer = proxyServer;
        this.directory = directory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;
        configManager = new ConfigManager();
        reload(false);
        serverManager = new ServerManager();
        serverManager.loadServers();

        proxyServer.getCommandManager().register("servermanager",new MainCommand());
        proxyServer.getCommandManager().register("sm",new MainCommand());
    }

    public void reload(boolean b){
        configManager.loadConfig();
        configManager.loadDatabase();
        Cache.load();
        if(b){
            serverManager.unLoadServers();
            serverManager.loadServers();
        }
    }
}
