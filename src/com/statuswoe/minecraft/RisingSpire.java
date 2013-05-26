package com.statuswoe.minecraft;

import java.lang.reflect.Method;
import java.util.Vector;

import net.minecraft.server.v1_5_R3.EntityTypes;

import org.bukkit.plugin.java.JavaPlugin;

import com.statuswoe.minecraft.entities.DaySkeleton;
import com.statuswoe.minecraft.entities.DayZombie;

public final class RisingSpire extends JavaPlugin {

	public Vector<Spire> spires = new Vector<Spire>();
	public MobSpawner spawner;
	
	@Override
    public void onEnable(){
        // Logic to be performed when the plugin is enabled
		getLogger().info("onEnable has been invoked!");
		// This will throw a NullPointerException if you don't have the command defined in your plugin.yml file!
		getCommand("start").setExecutor(new RSCommandExecutor(this));
		getCommand("coord").setExecutor(new RSCommandExecutor(this));
		getCommand("daywalker").setExecutor(new RSCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new DamageListener(), this);
		
		try{
            @SuppressWarnings("rawtypes")
            Class[] args = new Class[3];
            args[0] = Class.class;
            args[1] = String.class;
            args[2] = int.class;
 
            Method a = EntityTypes.class.getDeclaredMethod("a", args);
            a.setAccessible(true);
 
            a.invoke(a, DayZombie.class, "Zombie", 54);//54
            a.invoke(a, DaySkeleton.class, "Skeleton", 51);//54
            //a.invoke(a, DayWalkerSkeleton.class, "Skeleton", 51);
        }catch (Exception e){
            e.printStackTrace();
            this.setEnabled(false);
        }
    }
 
    @Override
    public void onDisable() {
        //Logic to be performed when the plugin is disabled
    	getLogger().info("onDisable has been invoked!");
    }
    
    
   
 
}
