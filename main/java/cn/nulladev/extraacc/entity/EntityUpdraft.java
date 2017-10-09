package cn.nulladev.extraacc.entity;

import java.util.List;

import cn.lambdalib.util.generic.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityUpdraft extends EntityHasOwner {
	
	public static final int BASIC_AGE = 100;
	public static final int MAX_AGE = 160;
	public static final float BASIC_VELOCITY = 0.2F;
	public static final float MAX_VELOCITY = 0.3F;
	public static final float BASIC_DAMAGE = 2;
	public static final float MAX_DAMAGE = 3;
	
	public static final float DECREASE_RATE = 0.8F;
	public static final float INITIAL_WIDTH = 1.2F;
	public static final float INITIAL_HEIGHT = 2.4F;
	
	private float exp;
	private Vec3 direc;
	private final float velocityDecreaseRate = 1F;
    public final int age;

	public EntityUpdraft(World world) {
        super(world);
        this.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        this.age = MAX_AGE;
    }
	
    public EntityUpdraft(World world, EntityPlayer thrower, float _exp, Vec3 _dir) {
        super(world);
        this.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        this.exp = _exp;
        this.direc = _dir;
        this.age = getAge(_exp);
        this.setPosition(thrower.posX, thrower.posY + INITIAL_HEIGHT / 2, thrower.posZ);
        this.setVelocity(_dir, getVelocity(_exp));
        this.setOwner(thrower);
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
    public void setVelocity(double vx, double vy, double vz) {
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(vx * vx + vz * vz);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(vx, vz) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(vy, (double)f) * 180.0D / Math.PI);
        }
    }
    
    public void setVelocity(Vec3 vDir, float v) {
    	vDir.normalize();
    	this.setVelocity(v * vDir.xCoord, v * vDir.yCoord, v * vDir.zCoord );
    }
    
	@Override
	public void onUpdate() {

		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();

		this.ticksExisted++;
		this.width += 0.02;
		this.height += 0.04;

		Vec3 currentPos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 nextPos = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + 0.02, this.posZ + this.motionZ);
		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(currentPos, nextPos);

		if (movingobjectposition != null) {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Blocks.portal) {
				this.setInPortal();
			}
			nextPos = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}

		AxisAlignedBB axisalignedbb = this.boundingBox.expand(this.width, this.height, this.width);
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb.addCoord(this.motionX, 0, this.motionZ));
		EntityPlayer player = this.getOwner();

		for (Object o : list) {
			Entity entity = (Entity)o;
			if (entity == player) {
				continue;
			}
			entity.attackEntityFrom(DamageSource.causePlayerDamage(this.getOwner()).setProjectile(), getDamage(exp));
			//entity.setVelocity(this.motionX, 0.1, this.motionZ);
			//entity.isAirBorne = true;
			entity.addVelocity(this.motionX / 10, 0.02, this.motionZ / 10);
			entity.fallDistance = 0;
		}	

        this.posX = nextPos.xCoord;
        this.posY = nextPos.yCoord;
        this.posZ = nextPos.zCoord;

        float f2 = velocityDecreaseRate;
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                float f4 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }

            f2 = 0.8F;
        }

        this.motionX *= (double)f2;
        this.motionY *= (double)f2;
        this.motionZ *= (double)f2;
        
        this.setPosition(this.posX, this.posY, this.posZ);
        
        this.rotationYaw += 5;

        if (this.ticksExisted >= this.age)
        	this.setDead();
    }

}