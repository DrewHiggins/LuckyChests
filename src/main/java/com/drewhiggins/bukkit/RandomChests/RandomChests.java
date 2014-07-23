package com.drewhiggins.bukkit.RandomChests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class RandomChests extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        // loads values from config
        long delayTime = this.getConfig().getLong("delayTime"); // the time the user has selected to delay the first chest spawning
        long spawnTime = this.getConfig().getLong("spawnTime"); // the time the user has selected between chest spawns
        boolean unspawnPreviousChest = this.getConfig().getBoolean("unspawnPreviousChest");
        World world = Bukkit.getWorld(this.getConfig().getString("world"));
        List<String> allowedItemsAsStrings = RandomChests.this.getConfig().getStringList("allowedItems");
        List<Material> allowedItems = new ArrayList<Material>();
        for (String s : allowedItemsAsStrings) { // converts the string item names to material enums
            allowedItems.add(Material.getMaterial(s));
        }
        int numItems = this.getConfig().getInt("numItems");

        // forms array of min/max values from config [minX, maxX, minZ, maxZ]
        int[] extrema = new int[4];
        extrema[0] = this.getConfig().getInt("minX");
        extrema[1] = this.getConfig().getInt("maxX");
        extrema[2] = this.getConfig().getInt("minZ");
        extrema[3] = this.getConfig().getInt("maxZ");


        // disables plugin if world not found
        if (world == null) {
            getLogger().info("Cannot find world! Check the config file.");
            this.setEnabled(false);
        } else {
            getLogger().info("Random Chests has been enabled!");
            new ChestSpawnTask(this, unspawnPreviousChest, allowedItems, numItems, world, extrema).runTaskTimer(this, delayTime, spawnTime);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Random chests has been disabled!");
    }

}
