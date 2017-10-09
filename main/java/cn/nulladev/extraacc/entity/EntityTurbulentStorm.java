package cn.nulladev.extraacc.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cn.lambdalib.util.generic.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityTurbulentStorm extends EntityFollowOwner {
	
	private static final float BASIC_WIDTH = 6;
	private static final float BASIC_HEIGHT = 4;
	private static final float BASIC_D = 16;
	private static final float RATE = 1.5F;
	
	private final float exp;
	
	public EntityTurbulentStorm(World _world) {
		super(_world, Minecraft.getMinecraft().thePlayer);
		this.exp = 0;
	}
	
	public EntityTurbulentStorm(World _world, EntityPlayer _owner, float _exp) {
		super(_world, _owner, getWidth(_exp), getHeight(_exp), Integer.MAX_VALUE);
		this.exp = _exp;
	}
	
	public static float getWidth(float _exp) {
		return 2 * MathUtils.lerpf(BASIC_WIDTH, BASIC_WIDTH * RATE, _exp);
	}
	
	public static float getHeight(float _exp) {
		return 2 * MathUtils.lerpf(BASIC_HEIGHT, BASIC_HEIGHT * RATE, _exp);
	}
	
	public void onUpdate() {
		super.onUpdate();
		List list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(
				posX - getWidth(exp) / 2, posY - getHeight(exp) / 2 - 1, posZ - getWidth(exp) / 2, posX + getWidth(exp) / 2, posY + getHeight(exp) / 2 - 1, posZ + getWidth(exp) / 2));
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			EntityLivingBase entity = (EntityLivingBase)iterator.next();
			if (entity == this.getOwner())
				continue;
			entity.attackEntityFrom(DamageSource.causePlayerDamage(this.getOwner()).setProjectile(), getDamage(exp));
		}
	}
	
	private float getBasicDamage(float exp) {
    	return MathUtils.lerpf(BASIC_D, RATE, exp);
    }
    
    private float getDamage(float exp) {
    	return new Random().nextFloat() * getBasicDamage(exp);
    }
	
	

}
