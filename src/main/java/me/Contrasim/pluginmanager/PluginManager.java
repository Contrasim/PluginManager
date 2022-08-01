package me.Contrasim.pluginmanager;

import com.google.common.io.ByteStreams;
import me.Contrasim.pluginmanager.commands.PluginManagerCommand;
import me.Contrasim.pluginmanager.utils.messagesUtil;
import me.Contrasim.pluginmanager.utils.pluginUtil;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class PluginManager extends JavaPlugin {

    public Configuration config;
    public Configuration messages;
    public File configFile = new File(getDataFolder(), "config.yml");
    public File messagesFile = new File(getDataFolder(), "messages.yml");
    private static PluginManager instance;
    public messagesUtil messagesUtil = new messagesUtil();
    public pluginUtil pluginUtil = new pluginUtil(this);


    @Override
    public void onEnable() {

        setInstance(this);

        this.getLogger().info("Attempting to load configurations...");
        try {
            loadConfig();
        } catch (IOException e) {
            this.getLogger().severe("Failed to load configuration!");
        }

        getCommand("plugman").setExecutor(new PluginManagerCommand(this));

        getLogger().info("The plugin has been successfully loaded and enabled!");

    }

    @Override
    public void onDisable() {
        this.getLogger().info("The plugin has been successfully unloaded and disabled!");
    }

    public void loadConfig () throws IOException {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        if (!configFile.exists()) {
            this.getLogger().warning("Configuration not found, creating a config new file...");
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("config.yml");
                OutputStream os = new FileOutputStream(configFile);
                ByteStreams.copy(is, os);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create a new configuration file!", e);
            }
        }
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);

        if (!messagesFile.exists()) {
            try {
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("messages.yml");
                OutputStream os = new FileOutputStream(messagesFile);
                ByteStreams.copy(is, os);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create the messages file!", e);
            }
        }
        messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(messages, messagesFile);
    }

    public static PluginManager getInstance () {
        return instance;
    }

    private static void setInstance (PluginManager instance) {
        PluginManager.instance = instance;
    }

}
