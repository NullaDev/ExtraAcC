package cn.nulladev.extraacc.entity;

import cn.lambdalib.util.generic.MathUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityWindBlade extends EntityFlying {
	
	public static final int BASIC_AGE = 100;
	public static final int MAX_AGE = 160;
	
	public static final float BASIC_VELOCITY = 1F;
	public static final float MAX_VELOCITY = 1.6F;
	
	public static final float BASIC_DAMAGE = 12;
	public static final float MAX_DAMAGE = 18;
	
	public static final float DECREASE_RATE = 0.8F;
	public static final float SIZE = 1F;
	
	private float exp;
	private Vec3 direc;
	
	private double offsetX = 0F;
	private double offsetZ = 0F;

	public EntityWindBlade(World world) {
        super(world, SIZE, SIZE);
    }
	
    public EntityWindBlade(World world, EntityPlayer thrower, float _exp, Vec3 _dir) {
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
    
    private float getBasicDamage(float exp) {
    	return MathUtils.lerpf(BASIC_DAMAGE, MAX_DAMAGE, exp);
    }
    
    private float getDamage(float exp) {
    	return getBasicDamage(exp) * MathUtils.lerpf(1, DECREASE_RATE, (float)this.ticksExisted / this.age);
    }

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if (pos.entityHit != null) {
			pos.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(this.getOwner()).setProjectile(), getDamage(exp));
		}
		this.setDead();
	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		Vec3 xzVec = Vec3.createVectorHelper(this.motionX, 0, this.motionZ).normalize();
		this.posX -= offsetX;
		this.posZ -= offsetZ;
		offsetX = xzVec.zCoord * Math.sin(this.ticksExisted * Math.PI / 10) * 10 * getVelocity(exp);
		offsetZ = - xzVec.xCoord * Math.sin(this.ticksExisted * Math.PI / 10) * 10 * getVelocity(exp);
		this.posX += offsetX;
		this.posZ += offsetZ;
	}

}