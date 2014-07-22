package com.drewhiggins.bukkit.RandomChests;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

public class RandomChests extends JavaPlugin {

    private final World world = getServer().getWorld("world");
    private final int MAX_X = 25000; // range extrema for locations randomly generated
    private final int MAX_Z = 25000;
    private final int MIN_X = -25000;
    private final int MIN_Z = -25000;

    @Override
    public void onEnable() {
        getLogger().info("Random Chests has been enabled!");
        BukkitScheduler scheduler = getServer().getScheduler();
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
        int randX = random.nextInt(MAX_X - MIN_X) + MIN_X;
        int randZ = random.nextInt(MAX_Z - MIN_Z) + MIN_Z;
        int randY = world.getHighestBlockYAt(randX, randZ);
        return new Location(world, randX, randY, randZ);
    }

    public String locationToString(Location location) {
        return "(X: " + location.getX() + ", Y: " +location.getY() + ", Z: " + location.getZ() + ")";
    }

    public void spawnChest(Location location) {
        Block block = location.getBlock();
        block.setType(Material.CHEST);
        getServer().broadcastMessage("A new chest has spawned at " + locationToString(location));
    }

}
