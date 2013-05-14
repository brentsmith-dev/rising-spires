package com.statuswoe.minecraft;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RSCommandExecutor implements CommandExecutor {

	private RisingSpire plugin;
	
	public RSCommandExecutor(RisingSpire plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if (command.getName().equalsIgnoreCase("start")){
			plugin.getServer().broadcastMessage("IT HAS BEGUN!");
			createSpire(player);
			return true;
		}else if (command.getName().equalsIgnoreCase("coord")){
			sender.sendMessage("x: " + player.getLocation().getBlockX() +
					" y :" + player.getLocation().getBlockY() +
					" z: " + player.getLocation().getBlockZ());
			return true;
		}else{
			return false;
		}
	}
	
	private void createSpire(Player player){
		Location loc = player.getLocation();
		World w = loc.getWorld();
		final Spire spire = new Spire(w, loc, plugin);
		plugin.spires.add(spire);
		// Start the timer
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			public void run(){
				if (spire.height < 225){
					//plugin.getServer().broadcastMessage("Spire Growing!");
					spire.grow();
				}
			}
		}, 60, 60);
	}
	
	
	

}
