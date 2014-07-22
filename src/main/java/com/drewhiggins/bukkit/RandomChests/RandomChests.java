package com.drewhiggins.bukkit.RandomChests;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
                Bukkit.getServer().broadcastMessage("A new chest has spawned at " + locationToString(randomLocation));
            }
        }, 0L, 1200L);
    }

    @Override
    public void onDisable() {
        getLogger().info("Random chests has been disabled!");
    }

    public Location getRandomLocation() {
        Random random = new Random();
        double randX = random.nextDouble();
        double initRandY = random.nextDouble();
        double randZ = random.nextDouble();
        Location initLoc = new Location(world, randX, initRandY, randZ);
        double randY = world.getHighestBlockYAt(initLoc);
        return new Location(world, randX, randY, randZ);
    }

    public String locationToString(Location location) {
        return "(X: " + location.getX() + ", Y: " +location.getY() + ", Z: " + location.getZ() + ")";
    }

}
