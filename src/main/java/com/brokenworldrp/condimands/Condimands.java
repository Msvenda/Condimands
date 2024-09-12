package com.brokenworldrp.condimands;

import com.brokenworldrp.condimands.commands.WipeTagsCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Condimands extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginCommand("tagchecker").setExecutor(new WipeTagsCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
