package com.statuswoe.minecraft;

import java.util.Vector;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

public final class RisingSpire extends JavaPlugin {

	public Vector<Spire> spires = new Vector<Spire>();
	
	@Override
    public void onEnable(){
        // Logic to be performed when the plugin is enabled
		getLogger().info("onEnable has been invoked!");
		// This will throw a NullPointerException if you don't have the command defined in your plugin.yml file!
		getCommand("start").setExecutor(new RSCommandExecutor(this));
		getCommand("coord").setExecutor(new RSCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new DamageListener(), this);
    }
 
    @Override
    public void onDisable() {
        //Logic to be performed when the plugin is disabled
    	getLogger().info("onDisable has been invoked!");
    }
    
   
 
}
