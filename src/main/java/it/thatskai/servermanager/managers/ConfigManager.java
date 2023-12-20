package it.thatskai.servermanager.managers;

import com.velocitypowered.api.plugin.PluginContainer;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import it.thatskai.servermanager.VelocityPlugin;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Getter
public class ConfigManager {

    private YamlDocument config, database;

    public void loadConfig(){
        try{
            config = YamlDocument.create(new File(VelocityPlugin.getInstance().getDirectory().toFile(), "config.yml"),
                    Objects.requireNonNull(getClass().getResourceAsStream("/config.yml")),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("configuration-version"))
                            .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS).build());
            config.update();
            config.save();
        } catch (IOException ex){
            ex.printStackTrace();
            Optional<PluginContainer> plugin = VelocityPlugin.getInstance().getProxyServer().getPluginManager().getPlugin("velocity-server-manager");
            plugin.ifPresent(pluginContainer -> pluginContainer.getExecutorService().shutdown());
        }
    }
    public void loadDatabase(){
        try{
            database = YamlDocument.create(new File(VelocityPlugin.getInstance().getDirectory().toFile(), "database.yml"),
                    Objects.requireNonNull(getClass().getResourceAsStream("/database.yml")),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("configuration-version"))
                            .setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS).build());
            database.update();
            database.save();
        } catch (IOException ex){
            ex.printStackTrace();
            Optional<PluginContainer> plugin = VelocityPlugin.getInstance().getProxyServer().getPluginManager().getPlugin("velocity-server-manager");
            plugin.ifPresent(pluginContainer -> pluginContainer.getExecutorService().shutdown());
        }
    }
}
