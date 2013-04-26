package com.statuswoe.minecraft;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class Spire {
	World world;
	Location location;
	Plugin plugin;
	
	public Spire(World w, Location l, Plugin p){
		location = l.clone();
		location.setX(location.getBlockX() + 25);
		world = w;
		plugin = p;
		clearSpace();
	}
	
	private void clearSpace(){
		// fill in a base for the spire to start on where the bedrock will be erratic
		//for (int x = location.getBlockX() - 10; x <= location.getBlockX() + 10; x++){
		//	for (int z = location.getBlockZ() - 10; z <= location.getBlockZ() + 10; z++){
		//		world.getBlockAt(new Location(world, x, location.getBlockY() -1, z)).setType(Material.BEDROCK);
		//	}
		//}
		
		// The location of the center of the spire, starting at bedrock
		//location.setY(5); // 5 can't have bedrock which might muffle the explosion
		
		// First blow shit up and create an area from which to grow
		// this makes a substantial cavern
		//world.createExplosion(location, 50, true);
		//world.createExplosion(location, 50, true);
		//world.createExplosion(location, 50, true);
		
		
		createHeart();
		
	}
	
	private void createHeart(){
		// we're going to use a beacon for the heart
		Location tempLocation = location.clone();
		createPad(tempLocation.getBlockX()-1, tempLocation.getBlockY(), tempLocation.getBlockZ()-1, 3, Material.IRON_BLOCK);
		world.getBlockAt(tempLocation.getBlockX(), tempLocation.getBlockY() + 1, tempLocation.getBlockZ()).setType(Material.BEACON);
		
		// start building spire core
//		tempLocation.setX(tempLocation.getBlockX()+3);
//		for (int z = tempLocation.getBlockZ()-3; z <=  tempLocation.getBlockZ()+3; z++){
//			world.getBlockAt(new Location(world, tempLocation.getBlockX(), 5, z)).setType(Material.BEDROCK);
//			
//		}
		for (int y = 0; y < 4; y++){
			buildCore(tempLocation, y);
		}
	}
	
	// TODO: it might be nice to be able to just define an array of block types and
	// a location and just have a build(array,loc) util function
	private void buildCore(Location loc, int level){
		for (int z = loc.getBlockZ() - 3; z <= loc.getBlockZ() +3; z++){
			setBedrock(loc.getBlockX() - 3, loc.getBlockY() + level, z);
		}
		for (int z = loc.getBlockZ() - 3; z <= loc.getBlockZ() +3; z++){
			setBedrock(loc.getBlockX() + 3, loc.getBlockY() + level, z);
		}
		for (int x = loc.getBlockX() - 2; x <= loc.getBlockX() +2; x++){
			setBedrock(x, loc.getBlockY() + level, loc.getBlockZ() - 3);
			setBedrock(x, loc.getBlockY() + level, loc.getBlockZ() + 3);
		}
		placeStair(loc, level);
	}
	
	// we should be able to generate this based on the level
	private void placeStair(Location loc, int level){
		// It would be neat if we could turn this into an algorithm with variable size
		level = loc.getBlockY() + level;
		plugin.getServer().broadcastMessage("Level " + level);
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
