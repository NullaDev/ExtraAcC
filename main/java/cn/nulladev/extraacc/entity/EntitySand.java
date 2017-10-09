package cn.nulladev.extraacc.entity;

import java.util.Random;

import cn.lambdalib.util.generic.MathUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySand extends EntityFlying {

	public static final int BASIC_AGE = 60;
	public static final int MAX_AGE = 120;
	public static final float BASIC_VELOCITY = 1.6F;
	public static final float MAX_VELOCITY = 2.4F;
	public static final float BASIC_DAMAGE = 4;
	public static final float MAX_DAMAGE = 6;
	
	public static final float SIZE = 0.2F;
	
	private float exp;
	private Vec3 direc;

	public EntitySand(World world) {
        super(world, SIZE, SIZE);
    }
	
    public EntitySand(World world, EntityPlayer thrower, float _exp, Vec3 _dir) {
        super(world, thrower, thrower.posX, thrower.posY + thrower.eyeHeight, thrower.posZ, getSize(_exp), getSize(_exp), getAge(_exp));
        this.setGravity(0.01f);
        this.setDecrease(0.98F);
        this.exp = _exp;
        this.direc = _dir;
        this.setVelocity(_dir, getVelocity(_exp));
    }
    
    private static float getSize(float exp) {
    	return SIZE * (MathUtils.lerpf(1, 2, exp) * new Random().nextFloat() + 0.5F);
    }
    
    private static int getAge(float exp) {
    	return (int) MathUtils.lerpf(BASIC_AGE, MAX_AGE, exp);
    }
    
    private static float getVelocity(float exp) {
    	return MathUtils.lerpf(BASIC_VELOCITY, MAX_VELOCITY, exp) * (new Random().nextFloat() + 0.5F);
    }
    
    private float getDamage(float exp) {
    	return MathUtils.lerpf(BASIC_DAMAGE, MAX_DAMAGE, exp) * (new Random().nextFloat() + 0.5F);
    }

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if (pos.entityHit != null) {
			pos.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(this.getOwner()).setProjectile(), getDamage(exp));
			if (pos.entityHit instanceof EntityLivingBase) {
				EntityLivingBase elb = (EntityLivingBase) pos.entityHit;
				if (!elb.isPotionActive(Potion.blindness)) {
					elb.addPotionEffect(new PotionEffect(Potion.blindness.id, (int)MathUtils.lerp(40, 80, exp)));
				}
			}
		}
		this.setDead();
	}

}
