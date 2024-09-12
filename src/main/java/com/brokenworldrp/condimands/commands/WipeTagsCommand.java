package com.brokenworldrp.condimands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WipeTagsCommand implements CommandExecutor {

    //Map holds UUID of player for tag wiping and name of request owner (name is used to allow non-player usage (ex. console))
    static Map<UUID, String> confirmationMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2 || args.length < 1) {
            return false;
        }
        String playerName = args[0];
        boolean skipCheck = false;
        if (args.length == 2) {
            skipCheck = args[1].equalsIgnoreCase("-force");
        }

        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            sender.sendMessage(String.format("%sPLayer with name '%s' not found.", ChatColor.RED, playerName));
            return false;
        }

        if (
                skipCheck ||
                        (confirmationMap.containsKey(player.getUniqueId()) && confirmationMap.get(player.getUniqueId()).equals(sender.getName()))
        ) {
            int removedTags = removeTags(player);
            sender.sendMessage(String.format("%sRemoved %d tags from player '%s'", ChatColor.GREEN, removedTags, playerName));
            confirmationMap.remove(player.getUniqueId());
        } else {
            confirmationMap.put(player.getUniqueId(), sender.getName());
            sender.sendMessage(String.format("%sThis will remove ALL scoreboard tags from '%s', please run the command again to confirm.", ChatColor.YELLOW, playerName));
        }
        return true;
    }

    private int removeTags(Player player) {
        int playerTags = player.getScoreboardTags().size();
        for (String tag : player.getScoreboardTags()) {
            player.removeScoreboardTag(tag);
        }
        return playerTags;
    }
}