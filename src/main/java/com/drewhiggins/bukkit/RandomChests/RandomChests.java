package com.drewhiggins.bukkit.RandomChests;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

public class RandomChests extends JavaPlugin {

    private final World world = Bukkit.getWorld("world");

    @Override
    public void onEnable() {
        getLogger().info("Random Chests has been enabled!");
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Location randomLocation = getRandomLocation();
                spawnChest(randomLocation);
            }
        }, 0L, 2400L); // spawns every 2400 ticks or 2 minutes
    }

    @Override
    public void onDisable() {
        getLogger().info("Random chests has been disabled!");
    }

    public Location getRandomLocation() {
        Random random = new Random();
        int randX = random.nextInt();
        int randZ = random.nextInt();
        int randY = world.getHighestBlockYAt(randX, randZ);
        return new Location(world, randX, randY, randZ);
    }

    public String locationToString(Location location) {
        return "(X: " + location.getX() + ", Y: " +location.getY() + ", Z: " + location.getZ() + ")";
    }

    public void spawnChest(Location location) {
        Block block = location.getBlock();
        block.setType(Material.CHEST);
        Bukkit.getServer().broadcastMessage("A new chest has spawned at " + locationToString(location));
    }

}
