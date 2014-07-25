package com.drewhiggins.bukkit.LuckyChests;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class ChestSpawnTask extends BukkitRunnable{

    private final JavaPlugin plugin;
    private boolean unspawnPreviousChest;
    private List<Material> allowedItems;
    private World world;
    private int numItems;

    // range extrema for locations randomly generated
    private int MIN_X;
    private int MAX_X;
    private int MIN_Z;
    private int MAX_Z;

    private Location currentLocation; // stores the location of the current chest

    public ChestSpawnTask(JavaPlugin plugin, boolean unspawnPreviousChest, List<Material> allowedItems, int numItems, World world, int[] extrema) {
        this.plugin = plugin;
        this.unspawnPreviousChest = unspawnPreviousChest;
        this.allowedItems = allowedItems;
        this.numItems = numItems;
        this.world = world;
        this.MIN_X = extrema[0];
        this.MAX_X = extrema[1];
        this.MIN_Z = extrema[2];
        this.MAX_Z = extrema[3];
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
        Chest chest = (Chest)block.getState();
        for (int i = 0; i < numItems; i++) {
            Random random = new Random();
            int randIndex = random.nextInt(allowedItems.size());
            chest.getInventory().addItem(new ItemStack(allowedItems.get(randIndex), 1));
        }
        Bukkit.getServer().broadcastMessage("A new chest has spawned at " + locationToString(location));
    }

    public void unspawnChestAtLocation(Location location) {
        Block block = location.getBlock();
        Chest chest = (Chest)block.getState();
        chest.getInventory().clear();
        block.setType(Material.AIR);
        Bukkit.getServer().broadcastMessage("Chest at " + locationToString(location) + " has despawned!");
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
