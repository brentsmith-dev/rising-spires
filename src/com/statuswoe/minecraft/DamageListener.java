/**
 * 
 */
package com.statuswoe.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * @author bsmith
 *
 */
public class DamageListener implements Listener {

	@EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(e.getCause().equals(DamageCause.BLOCK_EXPLOSION)){
            e.setCancelled(true);
        }
    }
	

}
