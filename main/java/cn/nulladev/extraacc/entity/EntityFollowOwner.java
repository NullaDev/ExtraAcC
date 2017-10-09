package cn.nulladev.extraacc.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class EntityFollowOwner extends EntityHasOwner {
	
	private final int age;

	public EntityFollowOwner(World _world, EntityPlayer _owner) {
		super(_world);
		this.setOwner(_owner);
		this.age = Integer.MAX_VALUE;
	}
	
	public EntityFollowOwner(World _world, EntityPlayer _owner, float _width, float _height, int _age) {
		super(_world);
		this.setOwner(_owner);
		this.age = _age;
		this.setSize(_width, _height);
		this.setPosition(this.getOwner().posX, this.getOwner().posY, this.getOwner().posZ);
	}
	
	public void onUpdate() {
		super.onUpdate();
		if (this.ticksExisted >= this.age) {
			this.setDead();
			return;
		}
		if (this.getOwner() != null)
			this.setPosition(this.getOwner().posX, this.getOwner().posY, this.getOwner().posZ);
	}

}
