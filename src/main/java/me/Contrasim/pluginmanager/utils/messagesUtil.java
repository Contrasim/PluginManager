package me.Contrasim.pluginmanager.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class messagesUtil
{

    public void sendColoredMessage(CommandSender sender, String message)
    {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendColoredMessagePlaceholders(CommandSender sender, String message, String placeholder, String placeholderReplacement, String placeholderOne, String placeholderOneReplacement)
    {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message)
                .replaceAll("%" + placeholder + "%", placeholderReplacement)
                .replaceAll("%" + placeholderOne + "%", placeholderOneReplacement));
    }

}
