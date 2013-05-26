package com.statuswoe.minecraft;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class Spire {
	World world;
	Location location;
	RisingSpire plugin;
	int height = 0;
	
	public Spire(World w, Location l, RisingSpire p){
		location = l.clone();
		location.setY(6);
		world = w;
		plugin = p;
		
		
		// Create the large cavern at the bedrock where the spire will start
		clearSpace();
		
		// Create the base and beacon
		createHeart();
		
		// Grow the initial levels of the spire
		for (int y = 0; y < 5; y++){
			buildCore();
			growObsidian();
			height++;
		}
		
		
	}
	
	public void grow(){
		growObsidian();
		checkCavern();
		growCore();
	}
	
	private void checkCavern(){
		// These are the blocks I don't blow up
		for (int x = location.getBlockX() - 20; x <= location.getBlockX() + 20; x++){
			for (int z = location.getBlockZ() - 20; z <= location.getBlockZ() + 20; z++){
				// If there is an element found that is not in the unblowupables list we need to blow it up
				//if (!Arrays.asList(unblowupable).contains(world.getBlockAt(x,(location.getBlockY() + height+1),z).getType())){
				if (world.getBlockAt(x,(location.getBlockY() + height+1),z).getType() != Material.AIR){
					boom(x,location.getBlockY() + height+1,z);
				}
			}
		}
	}
	
	private void boom(int x, int y, int z){
		Location boomLoc = location.clone();
		boomLoc.setY(location.getBlockY() + height);
		world.createExplosion(x,y,z,10,true);
	}
	
	private void growCore(){
		buildCore();
		height++;
	}
	
	private void buildCore(){
		for (int z = location.getBlockZ() - 3; z <= location.getBlockZ() +3; z++){
			setBedrock(location.getBlockX() - 3, location.getBlockY() + height, z);
		}
		for (int z = location.getBlockZ() - 3; z <= location.getBlockZ() +3; z++){
			setBedrock(location.getBlockX() + 3, location.getBlockY() + height, z);
		}
		for (int x = location.getBlockX() - 2; x <= location.getBlockX() +2; x++){
			setBedrock(x, location.getBlockY() + height, location.getBlockZ() - 3);
			setBedrock(x, location.getBlockY() + height, location.getBlockZ() + 3);
		}
		placeStair(location, height);
	}
	
	private void growObsidian(){
		int obsidianWidth = 8 - (height / 20);
		for (int x = location.getBlockX() - obsidianWidth; x <= location.getBlockX() + obsidianWidth; x++){
			for (int z = location.getBlockZ() - obsidianWidth; z <= location.getBlockZ() + obsidianWidth; z++){
				if (!((x > location.getBlockX() -4 && x < location.getBlockX() + 4) &&
					(z > location.getBlockZ() -4 && z < location.getBlockZ() + 4))){
					world.getBlockAt(x,location.getBlockY() +height,z).setType(Material.OBSIDIAN);
				}
			}
		}
		if (height != 5 && (height - 5) % 20 == 0){
			plugin.getLogger().info("obsedian level: " + height % 20 + " x:" + height/20);
			world.getBlockAt(location.getBlockX() + 8 + ((8-height)/20), height + 1, location.getBlockZ()).setType(Material.GOLD_BLOCK);
			world.getBlockAt(location.getBlockX() + 10 + ((8-height)/20), height + 1, location.getBlockZ()).setType(Material.IRON_BLOCK);
			Location spawnLocation = new Location(world, location.getBlockX() + 8 + ((8-height)/20), height +1, location.getBlockZ());
			plugin.spawner.spawn(spawnLocation);
			spawnLocation = new Location(world, location.getBlockX() - 8 - ((8-height)/20), height +1, location.getBlockZ());
			plugin.spawner.spawn(spawnLocation);
		}
		
	}
	
	private void clearSpace(){
		// Remove any water or lava or sand or gravel from the column above the spire as these things deaden the explosions
		Material[] materialToRemove = {Material.SAND, Material.GRAVEL, Material.WATER, Material.LAVA, Material.STATIONARY_LAVA, Material.STATIONARY_WATER};
		for (int y = 1; y < 200; y++){
			for (int x = location.getBlockX() - 20; x < location.getBlockX() + 20; x++){
				for (int z = location.getBlockZ() - 20; z < location.getBlockZ() + 20; z++){
					if (Arrays.asList(materialToRemove).contains(world.getBlockAt(x,y,z).getType())){
						world.getBlockAt(x,y,z).setType(Material.AIR);
					}	
				}
			}
		}
		
		// First blow shit up and create an area from which to grow
		// this makes a substantial cavern
		world.createExplosion(location, 50, true);
		world.createExplosion(location, 50, true);		
		world.createExplosion(location.getBlockX(), location.getBlockY() + 10, location.getBlockZ(), 50, true);
		world.createExplosion(location.getBlockX(), location.getBlockY() + 10, location.getBlockZ(), 50, true);
		world.createExplosion(location.getBlockX(), location.getBlockY() + 20, location.getBlockZ(), 50, true);		
		world.createExplosion(location.getBlockX(), location.getBlockY() + 20, location.getBlockZ(), 50, true);	
	}
	
	private void createHeart(){
		// we're going to use a beacon for the heart
		Location tempLocation = location.clone();
		// for the limited placement area we want to make sure the bedrock floor doen't have large pockets
		for (int i = 0; i < 6; i++){
			createPad(tempLocation.getBlockX()-20, i, tempLocation.getBlockZ()-30, 81, Material.BEDROCK);
		}
		// place some monster spawners
		//world.getBlockAt(location.getBlockX() + 10, 6, location.getBlockZ() + 10).setType(Material.MOB_SPAWNER);
		//world.getBlockAt(location.getBlockX() - 10, 6, location.getBlockZ() - 10).setType(Material.MOB_SPAWNER);
		
		// create the iron pad that the beacon needs to sit on in order to create the beam of light
		createPad(tempLocation.getBlockX()-1, tempLocation.getBlockY(), tempLocation.getBlockZ()-1, 3, Material.IRON_BLOCK);
		world.getBlockAt(tempLocation.getBlockX(), tempLocation.getBlockY() + 1, tempLocation.getBlockZ()).setType(Material.BEACON);
	}
	
	
	
	// we should be able to generate this based on the level
	private void placeStair(Location loc, int level){
		// It would be nice if we could turn this into an algorithm with variable size
		level = loc.getBlockY() + level;
		//plugin.getServer().broadcastMessage("Level " + level);
		switch (level % 16){
			case 0 :  setBedrock(loc.getBlockX() - 2, level, loc.getBlockZ() - 2);break;
			case 1 :  setBedrock(loc.getBlockX() - 1, level, loc.getBlockZ() - 2);break;
			case 2 :  setBedrock(loc.getBlockX()    , level, loc.getBlockZ() - 2);break;
			case 3 :  setBedrock(loc.getBlockX() + 1, level, loc.getBlockZ() - 2);break;
			case 4 :  setBedrock(loc.getBlockX() + 2, level, loc.getBlockZ() - 2);break;
			case 5 :  setBedrock(loc.getBlockX() + 2, level, loc.getBlockZ() - 1);break;
			case 6 :  setBedrock(loc.getBlockX() + 2, level, loc.getBlockZ())    ;break;
			case 7 :  setBedrock(loc.getBlockX() + 2, level, loc.getBlockZ() + 1);break;
			case 8 :  setBedrock(loc.getBlockX() + 2, level, loc.getBlockZ() + 2);break;
			case 9 :  setBedrock(loc.getBlockX() + 1, level, loc.getBlockZ() + 2);break;
			case 10 : setBedrock(loc.getBlockX()    , level, loc.getBlockZ() + 2);break;
			case 11 : setBedrock(loc.getBlockX() - 1, level, loc.getBlockZ() + 2);break;
			case 12 : setBedrock(loc.getBlockX() - 2, level, loc.getBlockZ() + 2);break;
			case 13 : setBedrock(loc.getBlockX() - 2, level, loc.getBlockZ() + 1);break;
			case 14 : setBedrock(loc.getBlockX() - 2, level, loc.getBlockZ())    ;break;
			case 15 : setBedrock(loc.getBlockX() - 2, level, loc.getBlockZ() - 1);break;
		}
	}
	
	private void createPad(int startX, int y, int startZ, int size, Material material){
		for (int x = 0; x < size; x++){
			for (int z = 0; z < size; z++){
				//plugin.getServer().broadcastMessage("block x: " + (startX + x) + " y:" + y + " z:" + (startZ + z));
				world.getBlockAt(startX + x, y, startZ + z).setType(material);
			}
		}
	}
	
	private void setBedrock(int x, int y, int z){
		world.getBlockAt(x,y,z).setType(Material.BEDROCK);
	}
	

}
