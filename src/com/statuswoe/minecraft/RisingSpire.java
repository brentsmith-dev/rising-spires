package com.statuswoe.minecraft;

import org.bukkit.plugin.java.JavaPlugin;

public final class RisingSpire extends JavaPlugin {

	@Override
    public void onEnable(){
        // TODO Insert logic to be performed when the plugin is enabled
		getLogger().info("onEnable has been invoked!");
		// This will throw a NullPointerException if you don't have the command defined in your plugin.yml file!
		getCommand("brent").setExecutor(new RSCommandExecutor(this));
		getCommand("coord").setExecutor(new RSCommandExecutor(this));
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    	getLogger().info("onDisable has been invoked!");
    }
 
}
