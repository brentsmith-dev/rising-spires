package com.statuswoe.minecraft.entities;

import net.minecraft.server.v1_5_R3.EntitySkeleton;
import net.minecraft.server.v1_5_R3.IRangedEntity;
import net.minecraft.server.v1_5_R3.World;

public class DaySkeleton extends EntitySkeleton implements IRangedEntity {

	public DaySkeleton(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setOnFire(int i) {
		// don't set on fire
		//super.setOnFire(i);
	}

}
