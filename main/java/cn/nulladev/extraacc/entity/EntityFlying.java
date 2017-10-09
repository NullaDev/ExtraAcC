package cn.nulladev.extraacc.entity;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityFlying extends EntityHasOwner implements IProjectile {
	
    protected float gravity = 0.03F;
    protected float velocityDecreaseRate = 0.99F;
    public final int age;

    public EntityFlying(World world, float width, float height) {
        super(world);
        this.setSize(width, height);
        this.age = Integer.MAX_VALUE;
    }  

    public EntityFlying(World world, EntityPlayer thrower, double _posX, double _posY, double _posZ, float _width, float _height, int _age) {
        super(world);
        this.setOwner(thrower);
        this.setSize(_width, _height);
        this.setPosition(_posX, _posY, _posZ);
        this.yOffset = 0.0F;
        this.age = _age;
    }
    
    @Override
	public void setThrowableHeading(double vdx, double vdy, double vdz, float v_value, float p_70186_8_) {
		Vec3 vdr = Vec3.createVectorHelper(vdx, vdy, vdz).normalize();
		this.setVelocity(v_value * vdr.xCoord, v_value * vdr.yCoord, v_value * vdr.zCoord);
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

        Vec3 currentPos = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 nextPos = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(currentPos, nextPos);

        if (movingobjectposition != null) {
            nextPos = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        if (!this.worldObj.isRemote) {
            Entity flag = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ));
            EntityPlayer player = this.getOwner();

            for (int j = 0; j < list.size(); ++j) {
                Entity entity = (Entity)list.get(j);

                if (entity.canBeCollidedWith() && (entity != player)) {
                    MovingObjectPosition movingobjectposition1 = entity.boundingBox.calculateIntercept(currentPos, nextPos);
                    flag = entity;
                }
            }

            if (flag != null) {
                movingobjectposition = new MovingObjectPosition(flag);
            }
        }

        if (movingobjectposition != null) {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Blocks.portal) {
                this.setInPortal();
            } else {
                this.onImpact(movingobjectposition);
            }
        }

        this.posX = nextPos.xCoord;
        this.posY = nextPos.yCoord;
        this.posZ = nextPos.zCoord;
        double xz = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, xz) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

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
        
        this.motionY -= (double)this.gravity;
        this.setPosition(this.posX, this.posY, this.posZ);

        if (this.ticksExisted >= this.age)
        	this.setDead();
    }

    @Deprecated
    protected float getGravity() {
        return gravity;
    }
    
    @Deprecated
    protected void setGravity(float g) {
        this.gravity = g;
    }
    
    public void setNoGravity() {
        this.gravity = 0;
    }
    
    @Deprecated
    public void setDecrease(float f) {
        this.velocityDecreaseRate = f;
    }
    
    public void setNoDecrease() {
        this.velocityDecreaseRate = 1;
    }

    protected abstract void onImpact(MovingObjectPosition p_70184_1_);

    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }
}
