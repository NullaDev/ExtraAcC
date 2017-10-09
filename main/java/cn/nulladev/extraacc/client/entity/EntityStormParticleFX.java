package cn.nulladev.extraacc.client.entity;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityStormParticleFX extends EntityParticleFX {

	private final double randomDouble = new Random().nextDouble();
	private final double omegle = Math.PI / 60 + new Random().nextDouble() * Math.PI / 60;
	private final double y;
	private final double R;
	private double rad;
	private final EntityPlayer owner;
	private final int age = new Random().nextInt(200);
	
	private static final Vec3 ZERO_VEC3 = Vec3.createVectorHelper(0, 0, 0);

	public EntityStormParticleFX(World world, double posX, double posY, double posZ, EntityPlayer _owner) {
		super(world, posX, posY, posZ, ZERO_VEC3);
		this.particleMaxAge = Integer.MAX_VALUE;
		this.owner = _owner;

		y = this.posY - owner.posY;
		R = Math.sqrt((posX - owner.posX) * (posX - owner.posX) + (posZ - owner.posZ) * (posZ - owner.posZ));
		rad = Math.atan2(this.posY - owner.posY, this.posX - owner.posX);
		
		this.particleBlue = 1F;
		this.particleRed = new Random().nextFloat() * 0.25F + 0.5F;
		this.particleGreen = new Random().nextFloat() * 0.25F + 0.5F;
		
	}
	
	@Override
	public void onUpdate() {
		
		if (this.ticksExisted > this.age) {
			this.setDead();
			return;
		}
		
		rad += omegle;
		this.motionY = this.owner.posY - this.posY + y;
		this.motionX = this.owner.posX + R * Math.cos(rad) - this.posX;
		this.motionZ = this.owner.posZ + R * Math.sin(rad) - this.posZ;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
		this.ticksExisted++;
	}

	
}
