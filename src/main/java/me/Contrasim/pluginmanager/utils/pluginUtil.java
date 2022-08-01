package me.Contrasim.pluginmanager.utils;

import me.Contrasim.pluginmanager.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.*;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class pluginUtil
{

    messagesUtil messagesUtil = new messagesUtil();
    PluginManager mainPlugin;

    public pluginUtil(PluginManager plugin) {
        this.mainPlugin = plugin;
    }

    public void reloadPlugin(String plugin, CommandSender sender)
    {

        Plugin reloadingPlugin = Bukkit.getServer().getPluginManager().getPlugin(plugin);
        if (reloadingPlugin != null)
        {
            Bukkit.getPluginManager().disablePlugin(reloadingPlugin);
            Bukkit.getPluginManager().enablePlugin(reloadingPlugin);

            messagesUtil.sendColoredMessage(sender, "&aSucess! Reloaded the plugin &7" + plugin + " &asucessfully!");
        }
        else
        {
            messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + plugin + " &cdoes not exist!");
        }

    }

    public void enablePlugin(String plugin, CommandSender sender)
    {
        Plugin enablingPlugin = Bukkit.getServer().getPluginManager().getPlugin(plugin);
        if (enablingPlugin != null)
        {
            if (!enablingPlugin.isEnabled())
            {
                Bukkit.getPluginManager().enablePlugin(enablingPlugin);

                messagesUtil.sendColoredMessage(sender, "&aSucess! Enabled the plugin &7" + plugin + " &asucessfully!");
            }
            else
            {
                messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + plugin + " &cis already enabled!");
            }
        }
        else
        {
            messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + plugin + " &cdoes not exist!");
        }
    }

    public void disablePlugin(String plugin, CommandSender sender)
    {
        Plugin disablingPlugin = Bukkit.getServer().getPluginManager().getPlugin(plugin);
        if (disablingPlugin != null)
        {
            if (disablingPlugin.isEnabled())
            {
                Bukkit.getPluginManager().disablePlugin(disablingPlugin);

                messagesUtil.sendColoredMessage(sender, "&aSucess! Disabled the plugin &7" + plugin + " &asucessfully!");
            }
            else
            {
                messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + plugin + " &cis already disabled!");
            }
        }
        else
        {
            messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + plugin + " &cdoes not exist!");
        }
    }

    public void loadPlugin(String pluginName, CommandSender sender)
    {
        try {
            org.bukkit.plugin.PluginManager manager = Bukkit.getServer().getPluginManager();
            Plugin plugin = manager.loadPlugin(new File("plugins", pluginName + ".jar"));
            if (plugin == null) {
                messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + pluginName + " &cis an invalid plugin! (Trying using the exact file name)");
                return;
            }
            plugin.onLoad();
            manager.enablePlugin(plugin);
        } catch (Exception e) {
            messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + pluginName + " &cdoes not exist!");
            return;
        }
        if (sender instanceof Player) {
            messagesUtil.sendColoredMessage(sender, "&aSucess! Loaded the plugin &7" + pluginName + " &asucessfully!");
        }
    }

    public void unloadPlugin(String pluginName, CommandSender sender) throws Exception {
        org.bukkit.plugin.PluginManager manager = Bukkit.getServer().getPluginManager();
        SimplePluginManager spmanager = (SimplePluginManager)manager;
        if (spmanager != null) {
            Field pluginsField = spmanager.getClass().getDeclaredField("plugins");
            pluginsField.setAccessible(true);
            List<Plugin> plugins = (List<Plugin>)pluginsField.get(spmanager);
            Field lookupNamesField = spmanager.getClass().getDeclaredField("lookupNames");
            lookupNamesField.setAccessible(true);
            Map<String, Plugin> lookupNames = (Map<String, Plugin>)lookupNamesField.get(spmanager);
            Field commandMapField = spmanager.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap)commandMapField.get(spmanager);
            Field knownCommandsField;
            Map<String, Command> knownCommands = null;
            if (commandMap != null) {
                knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);
                knownCommands = (Map<String, Command>)knownCommandsField.get(commandMap);
            }
            for (Plugin plugin : manager.getPlugins()) {
                if (plugin.getDescription().getName().equalsIgnoreCase(pluginName)) {
                    manager.disablePlugin(plugin);
                    if (plugins != null && plugins.contains(plugin))
                        plugins.remove(plugin);
                    if (lookupNames != null && lookupNames.containsKey(pluginName))
                        lookupNames.remove(pluginName);
                    if (commandMap != null)
                        for (Iterator<Map.Entry<String, Command>> it = knownCommands.entrySet().iterator(); it.hasNext(); ) {
                            Map.Entry<String, Command> entry = it.next();
                            if (entry.getValue() instanceof PluginCommand) {
                                PluginCommand command = (PluginCommand)entry.getValue();
                                if (command.getPlugin() == plugin) {
                                    command.unregister(commandMap);
                                    it.remove();
                                }
                            }
                        }
                }
            }
        } else {
            messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + pluginName + " &cis already unloaded!");
        }
        if (sender instanceof Player)
            messagesUtil.sendColoredMessage(sender, "&aSucess! Unloaded the plugin &7" + pluginName + " &asucessfully!");
    }

    public void reloadConfig(String plugin, CommandSender sender)
    {
        Plugin reloadingConfig = Bukkit.getServer().getPluginManager().getPlugin(plugin);
        if (reloadingConfig != null)
        {

            reloadingConfig.reloadConfig();

            messagesUtil.sendColoredMessage(sender, "&aSucess! Reloaded &7" + plugin + "&7's &aconfiguration!");
        }
        else
        {
            messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + plugin + " &cdoes not exist!");
        }
    }

    public void pluginInfo(String plugin, CommandSender sender)
    {
        Plugin pluginInfo = Bukkit.getServer().getPluginManager().getPlugin(plugin);
        if (pluginInfo != null)
        {
            for (String plugmanHelp : mainPlugin.messages.getStringList("info")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugmanHelp).replaceAll("%name%",
                        pluginInfo.getName()).replaceAll("%authors%",
                        String.valueOf(pluginInfo.getDescription().getAuthors())).replaceAll("%version%",
                        pluginInfo.getDescription().getVersion()));
            }
        }
        else
        {
            messagesUtil.sendColoredMessage(sender, "&cError! The plugin &7" + plugin + " &cdoes not exist!");
        }
    }



}
