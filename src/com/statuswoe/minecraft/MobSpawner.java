package com.statuswoe.minecraft;

import net.minecraft.server.v1_5_R3.Item;
import net.minecraft.server.v1_5_R3.ItemStack;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.statuswoe.minecraft.entities.DaySkeleton;
import com.statuswoe.minecraft.entities.DayZombie;

public class MobSpawner {
	
	private RisingSpire plugin;
	private World world;
	
	public MobSpawner(RisingSpire spire, World xworld) {
		// TODO Auto-generated constructor stub
		plugin = spire;
		world = xworld;
	}
	
	public void spawn(Location location){
		plugin.getLogger().info("spawning random mob");
		Location spawnLocation = location.clone();
		spawnLocation.setX(location.getBlockX() + 10);
		net.minecraft.server.v1_5_R3.World mcWorld = ((CraftWorld) world).getHandle();
		DaySkeleton skeleton = new DaySkeleton(mcWorld);
		DayZombie zombie = new DayZombie(mcWorld);
       // DayWalkerZombie test = new DayWalkerZombie(mcWorld);
		skeleton.setPosition(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
		mcWorld.addEntity(skeleton, SpawnReason.CUSTOM);
		ItemStack bow = new ItemStack(Item.BOW);
		skeleton.setEquipment(0, bow);
		zombie.setPosition(spawnLocation.getX() + 5, spawnLocation.getY(), spawnLocation.getZ());
		mcWorld.addEntity(zombie, SpawnReason.CUSTOM);
	}
}
