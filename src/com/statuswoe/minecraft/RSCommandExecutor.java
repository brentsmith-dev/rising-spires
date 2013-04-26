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
		if (command.getName().equalsIgnoreCase("brent")){
			plugin.getServer().broadcastMessage("BRENT HAS SPOKEN!");
			buildSpire(player);
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
	
	//TODO: put this all in a spire class and don't be a dummy
	private void buildSpire(Player player){
		Location loc = player.getLocation();
		World w = loc.getWorld();
		plugin.spires.add(new Spire(w, loc, plugin));
	}
	
	
	

}
