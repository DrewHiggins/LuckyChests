package com.drewhiggins.bukkit.RandomChests;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomChests extends JavaPlugin {

    private World world = getServer().getWorld("world");

    @Override
    public void onEnable() {
        getLogger().info("Random Chests has been enabled!");
        new ChestSpawnTask(this, world, false).runTaskTimer(this, 10L, 2400L);
    }

    @Override
    public void onDisable() {
        getLogger().info("Random chests has been disabled!");
    }

}
