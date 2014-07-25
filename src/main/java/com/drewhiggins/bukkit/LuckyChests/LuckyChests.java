package com.drewhiggins.bukkit.LuckyChests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class LuckyChests extends JavaPlugin {

    private long delayTime;
    private long spawnTime;
    private boolean unspawnPreviousChest;
    private World world;
    private List<String> allowedItemsAsStrings;
    private List<Material> allowedItems;
    private int numItems;
    private int[] extrema = new int[4];

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        // loads values from config
        delayTime = this.getConfig().getLong("delayTime"); // the time the user has selected to delay the first chest spawning
        spawnTime = this.getConfig().getLong("spawnTime"); // the time the user has selected between chest spawns
        unspawnPreviousChest = this.getConfig().getBoolean("unspawnPreviousChest");
        world = Bukkit.getWorld(this.getConfig().getString("world"));
        allowedItemsAsStrings = LuckyChests.this.getConfig().getStringList("allowedItems");
        allowedItems = new ArrayList<Material>();
        for (String s : allowedItemsAsStrings) { // converts the string item names to material enums
            allowedItems.add(Material.getMaterial(s.toUpperCase()));
        }
        numItems = this.getConfig().getInt("numItems");
        // forms array of min/max values from config [minX, maxX, minZ, maxZ]
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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("lc")) {
            if (args[0].equalsIgnoreCase("newchest")) {
                if (sender.hasPermission("luckychests.spawn")) {
                    new ChestSpawnTask(this, unspawnPreviousChest, allowedItems, numItems, world, extrema).run();
                    return true;
                }
            }
            return false;
        }
        return false;
    }

}
