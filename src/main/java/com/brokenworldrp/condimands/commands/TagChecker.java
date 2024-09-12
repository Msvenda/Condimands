package com.brokenworldrp.condimands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class TagChecker implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            return false;
        }
        String playerName = args[0];

        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            sender.sendMessage(String.format("%sPLayer with name '%s' not found.", ChatColor.RED, playerName));
            return true;
        }

        String checkedTag = args[1];
        Set<String> playerTags = player.getScoreboardTags();
        if (playerTags.contains(checkedTag)) {
            sender.sendMessage(String.format("%sPlayer with name '%s' has tag '%s'. Not executing command.", ChatColor.YELLOW, playerName, checkedTag));
            return true;
        }

        String commandString = "";

        for (int i = 2; i < args.length; i++) {
            commandString += args[i] + " ";
        }

        try {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), commandString);
        } catch (Exception e) {
            String errorString = String.format("[Condimands] Could not execute command '%s': %e", commandString, e);
            Bukkit.getLogger().warning(errorString);
            sender.sendMessage(ChatColor.RED + errorString);
        }

        return true;
    }
}