package cn.nulladev.extraacc.entity;

import java.util.Random;

import cn.lambdalib.util.generic.MathUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityExplosiveAirGun extends EntityFlying {
	
	public static final int BASIC_AGE = 80;
	public static final int MAX_AGE = 144;
	public static final float BASIC_VELOCITY = 0.4F;
	public static final float MAX_VELOCITY = 0.6F;
	public static final float BASIC_STRENGTH = 3;
	public static final float MAX_STRENGTH = 8;
	
	public static final float SMALL_RATE = 0.05F;
	public static final float SMALL_STRENGTH = 0.2F;
	
	public static final float DECREASE_RATE = 0.5F;
	public static final float SIZE = 0.4F;
	
	private float exp;
	private Vec3 direc;

	public EntityExplosiveAirGun(World world) {
        super(world, SIZE, SIZE);
    }
	
    public EntityExplosiveAirGun(World world, EntityPlayer thrower, float _exp, Vec3 _dir) {
        super(world, thrower, thrower.posX, thrower.posY + thrower.eyeHeight, thrower.posZ, SIZE, SIZE, getAge(_exp));
        this.setNoGravity();
        this.setDecrease(0.99F);
        this.exp = _exp;
        this.direc = _dir;
        this.setVelocity(_dir, getVelocity(_exp));
    }
    
    private static int getAge(float exp) {
    	return (int) MathUtils.lerpf(BASIC_AGE, MAX_AGE, exp);
    }
    
    private static float getVelocity(float exp) {
    	return MathUtils.lerpf(BASIC_VELOCITY, MAX_VELOCITY, exp);
    }
    
    private float getBasicStrength(float exp) {
    	return MathUtils.lerpf(BASIC_STRENGTH, MAX_STRENGTH, exp);
    }
    
    private float getStrength(float exp) {
    	return getBasicStrength(exp) * MathUtils.lerpf(1, DECREASE_RATE, (float)this.ticksExisted / this.age);
    }

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if (pos.entityHit != null) {
			pos.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(this.getOwner()).setExplosion(), getStrength(exp));
		}
		this.explode();
	}
	
	public boolean canControl() {
		return !this.isDead && this.getOwner() != null;
	}
	
	public void explode() {
		this.worldObj.createExplosion(this.getOwner(), this.posX, this.posY, this.posZ, getStrength(exp), false);
		this.setDead();
	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		this.width -= SIZE * (1 - DECREASE_RATE) / getAge(exp);
		this.height -= SIZE * (1 - DECREASE_RATE) / getAge(exp);
		if (new Random().nextFloat() < SMALL_RATE) {
			this.worldObj.createExplosion(this.getOwner(), this.posX, this.posY, this.posZ, SMALL_STRENGTH, false);
		}
	}

}