package com.statuswoe.minecraft;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class Spire {
	World world;
	Location location;
	Plugin plugin;
	int height = 5;
	
	public Spire(World w, Location l, Plugin p){
		location = l.clone();
		location.setX(location.getBlockX() + 15);
		location.setY(6);
		world = w;
		plugin = p;
		
		
		// Create the large cavern at the bedrock where the spire will start
		clearSpace();
		
		// Create the base and beacon
		createHeart();
		
		// Grow the initial levels of the spire
		for (int y = 0; y < height; y++){
			buildCore(location, y);
		}
		
		
	}
	
	public void grow(){
		checkCavern();
		createHeart();
		growCore();
		growObsidian();
	}
	
	private void checkCavern(){
		// These are the blocks I don't blow up
		Material[] unblowupable = {Material.AIR, Material.FIRE, Material.WATER, Material.LAVA, Material.STATIONARY_LAVA, Material.STATIONARY_WATER};
		for (int x = location.getBlockX() - 10; x <= location.getBlockX() + 10; x++){
			for (int z = location.getBlockZ() - 10; z <= location.getBlockZ() + 10; z++){
				// If there is an element found that is not in the unblowupables list we need to blow it up
				if (!Arrays.asList(unblowupable).contains(world.getBlockAt(x,(location.getBlockY() + height+1),z).getType())){
					boom();
				}
				//TODO: Also maybe remove any standing water or lava
			}
		}
	}
	
	private void boom(){
		Location boomLoc = location.clone();
		boomLoc.setY(location.getBlockY() + height);
		world.createExplosion(boomLoc, 20, true);
		world.createExplosion(boomLoc, 20, true);
		world.createExplosion(boomLoc, 20, true);
		world.createExplosion(boomLoc, 20, true);
		world.createExplosion(boomLoc, 20, true);
		
		// use something like this to prevent player damage
		//@EventHandler
		//public void onEntityDamage(EntityDamageEvent e){
		//    if(e.getEntity() instanceof Player && e.getCause().equals(DamageCause.BLOCK_EXPLOSION){
		//        e.setCancelled(true);
		//   }
		//}
	}
	
	private void growCore(){
		buildCore(location, height+1);
		height++;
	}
	
	private void growObsidian(){
		
	}
	
	private void clearSpace(){
		// Remove any water or lava or sand or gravel from the column above the spire as these things deaden the explosions
		Material[] materialToRemove = {Material.SAND, Material.GRAVEL, Material.WATER, Material.LAVA, Material.STATIONARY_LAVA, Material.STATIONARY_WATER};
		for (int y = 1; y < 200; y++){
			for (int x = location.getBlockX() - 5; x < location.getBlockX() + 5; x++){
				for (int z = location.getBlockZ() - 5; z < location.getBlockZ() + 5; z++){
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
		world.createExplosion(location, 50, true);
		world.createExplosion(location, 50, true);
		world.createExplosion(location, 50, true);		
		world.createExplosion(location, 50, true);	
		world.createExplosion(location, 50, true);
		world.createExplosion(location, 50, true);		
		world.createExplosion(location, 50, true);	
		world.createExplosion(location, 50, true);
		world.createExplosion(location, 50, true);		
		world.createExplosion(location, 50, true);	
	}
	
	private void createHeart(){
		// we're going to use a beacon for the heart
		Location tempLocation = location.clone();
		// create the bedrock (stone for now so I can debug) floor the whole things is going to be sitting on
		createPad(tempLocation.getBlockX()-3, tempLocation.getBlockY(), tempLocation.getBlockZ()-3, 7, Material.STONE);
		// create the iron pad that the beacon needs to sit on in order to create the beam of light
		createPad(tempLocation.getBlockX()-1, tempLocation.getBlockY(), tempLocation.getBlockZ()-1, 3, Material.IRON_BLOCK);
		world.getBlockAt(tempLocation.getBlockX(), tempLocation.getBlockY() + 1, tempLocation.getBlockZ()).setType(Material.BEACON);
	}
	
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
		// It would be nice if we could turn this into an algorithm with variable size
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
