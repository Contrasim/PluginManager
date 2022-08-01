package me.Contrasim.pluginmanager.commands;

import me.Contrasim.pluginmanager.PluginManager;
import me.Contrasim.pluginmanager.utils.messagesUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;

public class PluginManagerCommand implements CommandExecutor
{

    PluginManager plugin;
    messagesUtil messagesUtil = new messagesUtil();

    public PluginManagerCommand(PluginManager plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, Command command, String label, String[] args)
    {

        if (args.length == 0)
        {
            for (String plugmanHelp : plugin.messages.getStringList("help")) {
                plugin.messagesUtil.sendColoredMessage(sender, plugmanHelp);
            }
        }

        if (args.length > 2)
        {
            for (String plugmanHelp : plugin.messages.getStringList("help")) {
                plugin.messagesUtil.sendColoredMessage(sender, plugmanHelp);
            }
        }

        if (args.length == 1)
        {

            if (args[0].equalsIgnoreCase("reload"))
            {
                for (String commandHelp : plugin.messages.getStringList("command")) {
                    plugin.messagesUtil.sendColoredMessagePlaceholders(sender,
                            commandHelp,
                            "command",
                            "§7Reload",
                            "usage",
                            "§7/plugman reload <plugin>");
                }
            }
            else if (args[0].equalsIgnoreCase("enable"))
            {
                for (String commandHelp : plugin.messages.getStringList("command")) {
                    plugin.messagesUtil.sendColoredMessagePlaceholders(sender,
                            commandHelp,
                            "command",
                            "§7Enable",
                            "usage",
                            "§7/plugman enable <plugin>");
                }
            }
            else if (args[0].equalsIgnoreCase("disable"))
            {
                for (String commandHelp : plugin.messages.getStringList("command")) {
                    plugin.messagesUtil.sendColoredMessagePlaceholders(sender,
                            commandHelp,
                            "command",
                            "§7Disable",
                            "usage",
                            "§7/plugman disable <plugin>");
                }
            }
            else if (args[0].equalsIgnoreCase("load"))
            {
                for (String commandHelp : plugin.messages.getStringList("command")) {
                    plugin.messagesUtil.sendColoredMessagePlaceholders(sender,
                            commandHelp,
                            "command",
                            "§7Load",
                            "usage",
                            "§7/plugman load <plugin>");
                }
            }
            else if (args[0].equalsIgnoreCase("reloadconfig"))
            {
                for (String commandHelp : plugin.messages.getStringList("command")) {
                    plugin.messagesUtil.sendColoredMessagePlaceholders(sender,
                            commandHelp,
                            "command",
                            "§7reloadConfig",
                            "usage",
                            "§7/plugman reloadconfig <plugin>");
                }
            }
            else if (args[0].equalsIgnoreCase("info"))
            {
                for (String commandHelp : plugin.messages.getStringList("command")) {
                    plugin.messagesUtil.sendColoredMessagePlaceholders(sender,
                            commandHelp,
                            "command",
                            "§7Info",
                            "usage",
                            "§7/plugman info <plugin>");
                }
            }
            else if (args[0].equalsIgnoreCase("unload"))
            {
                for (String commandHelp : plugin.messages.getStringList("command")) {
                    plugin.messagesUtil.sendColoredMessagePlaceholders(sender,
                            commandHelp,
                            "command",
                            "§7Unload",
                            "usage",
                            "§7/plugman unload <plugin>");
                }
            }
            else
            {
                for (String plugmanHelp : plugin.messages.getStringList("help")) {
                    plugin.messagesUtil.sendColoredMessage(sender, plugmanHelp);
                }
            }
        }

        if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("reload"))
            {
                if (!args[1].equalsIgnoreCase("PluginManager"))
                {
                    plugin.pluginUtil.reloadPlugin(args[1], sender);
                }
                else
                {
                    messagesUtil.sendColoredMessage(sender, "&cError! You cannot reload PluginManager!");
                }
            }
            else if (args[0].equalsIgnoreCase("enable"))
            {
                plugin.pluginUtil.enablePlugin(args[1], sender);
            }
            else if (args[0].equalsIgnoreCase("disable"))
            {
                if (!args[1].equalsIgnoreCase("PluginManager"))
                {
                    plugin.pluginUtil.disablePlugin(args[1], sender);
                }
                else
                {
                    messagesUtil.sendColoredMessage(sender, "&cError! You cannot disable PluginManager!");
                }
            }
            else if (args[0].equalsIgnoreCase("load"))
            {
                plugin.pluginUtil.loadPlugin(args[1], sender);
            }
            else if (args[0].equalsIgnoreCase("unload"))
            {
                if (!args[1].equalsIgnoreCase("PluginManager"))
                {
                    try {
                        plugin.pluginUtil.unloadPlugin(args[1], sender);
                    } catch (Exception e) {
                        plugin.pluginUtil.disablePlugin(args[1], sender);
                    }
                }
                else
                {
                    messagesUtil.sendColoredMessage(sender, "&cError! You cannot unload PluginManager!");
                }
            }
            else if (args[0].equalsIgnoreCase("reloadconfig"))
            {
                plugin.pluginUtil.reloadConfig(args[1], sender);
            }
            else if (args[0].equalsIgnoreCase("info"))
            {
                plugin.pluginUtil.pluginInfo(args[1], sender);
            }
            else
            {
                for (String plugmanHelp : plugin.messages.getStringList("help")) {
                    plugin.messagesUtil.sendColoredMessage(sender, plugmanHelp);
                }
            }
        }
        return false;
    }
}
