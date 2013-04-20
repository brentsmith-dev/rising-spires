package com.statuswoe.minecraft;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Spire {
	World world;
	Location location;
	
	public Spire(World w, Location l){
		location = l.clone();
		location.setX(location.getBlockX() + 25);
		world = w;
		
		clearSpace();
	}
	
	private void clearSpace(){
		// fill in a base for the spire to start on where the bedrock will be erratic
		for (int x = location.getBlockX() - 10; x <= location.getBlockX() + 10; x++){
			for (int z = location.getBlockZ() - 10; z <= location.getBlockZ() + 10; z++){
				world.getBlockAt(new Location(world, x, location.getBlockY() -1, z)).setType(Material.BEDROCK);
			}
		}
		
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
		world.getBlockAt(tempLocation).setType(Material.BEACON);
		
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
		int y = loc.getBlockY();
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
	
	private void placeStair(Location loc, int level){
		
	}
	
	// TODO: generalize this
	private void setBedrock(int x, int y, int z){
		world.getBlockAt(x,y,z).setType(Material.BEDROCK);
	}

}
