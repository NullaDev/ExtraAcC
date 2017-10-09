package cn.nulladev.extraacc.entity;

import cn.lambdalib.util.generic.MathUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBasicAirGun extends EntityFlying {
	
	public static final int BASIC_AGE = 100;
	public static final int MAX_AGE = 160;
	public static final float BASIC_VELOCITY = 0.4F;
	public static final float MAX_VELOCITY = 0.6F;
	public static final float BASIC_DAMAGE = 6;
	public static final float MAX_DAMAGE = 10;
	
	public static final float DECREASE_RATE = 0.5F;
	public static final float INITIAL_SIZE = 0.2F;
	
	private float exp;
	private Vec3 direc;

	public EntityBasicAirGun(World world) {
        super(world, INITIAL_SIZE, INITIAL_SIZE);
    }
	
    public EntityBasicAirGun(World world, EntityPlayer thrower, float _exp, Vec3 _dir) {
        super(world, thrower, thrower.posX, thrower.posY + thrower.eyeHeight, thrower.posZ, INITIAL_SIZE, INITIAL_SIZE, getAge(_exp));
        this.setNoGravity();
        this.setDecrease(0.98F);
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
    
    private float getBasicDamage(float exp) {
    	return MathUtils.lerpf(BASIC_DAMAGE, MAX_DAMAGE, exp);
    }
    
    private float getDamage(float exp) {
    	return getBasicDamage(exp) * MathUtils.lerpf(1, DECREASE_RATE, (float)this.ticksExisted / this.age);
    }

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if (pos.entityHit != null) {
			float value = this.getVelocity(exp) * MathUtils.lerpf(1, DECREASE_RATE, this.ticksExisted / this.age) * ( 2.0F / pos.entityHit.height);
			pos.entityHit.addVelocity(value * direc.xCoord, value * direc.yCoord, value * direc.zCoord);
			pos.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(this.getOwner()).setProjectile(), getDamage(exp));
		}
		this.setDead();
	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		this.width += 0.02F;
		this.height += 0.02F;
	}

}