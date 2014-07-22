package com.drewhiggins.bukkit.RandomChests;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class ChestSpawnTask extends BukkitRunnable{

    private final JavaPlugin plugin;
    private final World world;
    private boolean unspawnPreviousChest;
    // range extrema for locations randomly generated
    private final int MAX_X = 25000;
    private final int MAX_Z = 25000;
    private final int MIN_X = -25000;
    private final int MIN_Z = -25000;

    private Location currentLocation; // stores the location of the current chest

    public ChestSpawnTask(JavaPlugin plugin, World world, boolean unspawnPreviousChest) {
        this.plugin = plugin;
        this.world = world;
        this.unspawnPreviousChest = unspawnPreviousChest;
    }

    @Override
    public void run() {
        if (unspawnPreviousChest && currentLocation != null) {
            unspawnChestAtLocation(currentLocation);
        }
        currentLocation = getRandomLocation();
        spawnChestAtLocation(currentLocation);
    }

    public void spawnChestAtLocation(Location location) {
        Block block = location.getBlock();
        block.setType(Material.CHEST);
        Bukkit.getServer().broadcastMessage("A new chest has spawned at " + locationToString(location));
    }

    public void unspawnChestAtLocation(Location location) {
        Block block = location.getBlock();
        block.setType(Material.AIR);
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

}
