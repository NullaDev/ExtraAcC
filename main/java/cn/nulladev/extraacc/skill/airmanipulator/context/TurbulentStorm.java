package cn.nulladev.extraacc.skill.airmanipulator.context;

import java.util.Random;

import cn.academy.ability.api.context.Context;
import cn.academy.ability.api.context.Context.Status;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.lambdalib.util.generic.MathUtils;
import cn.nulladev.extraacc.client.entity.EntityParticleFX;
import cn.nulladev.extraacc.client.entity.EntityStormParticleFX;
import cn.nulladev.extraacc.entity.EntityTurbulentStorm;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillTurbulentStorm;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class TurbulentStorm extends Context {
	
	static final String MSG_PERFORM = "perform";
	static final String MSG_PERFORM2 = "perform2";
		
	public EntityTurbulentStorm entity;
	private boolean initialized = false;
	
	private float cp;

	public TurbulentStorm(EntityPlayer _player) {
		super(_player, SkillTurbulentStorm.INSTANCE);
		
		cp = MathUtils.lerpf(500, 400, ctx.getSkillExp());
	}
	
	private boolean consume() {
		float overload = MathUtils.lerpf(30, 50, ctx.getSkillExp());
		return ctx.consume(overload, cp);
	}
	
	private boolean tick() {
		float tick_cp = MathUtils.lerpf(50, 40, ctx.getSkillExp());
		return ctx.consume(1, tick_cp);
	}
	
	@Listener(channel=MSG_KEYDOWN, side=Side.CLIENT)
	public void l_keydown()  {
		sendToServer(MSG_PERFORM);
	}
	
	@Listener(channel=MSG_KEYUP, side=Side.CLIENT)
	public void l_keyup()  {
		sendToServer(MSG_PERFORM2);
	}
	
	@Listener(channel=MSG_PERFORM, side=Side.SERVER)
	public void s_perform()  {
		if(consume()) {
			World world = player.worldObj;
			entity = new EntityTurbulentStorm(world, player, ctx.getSkillExp());
			world.spawnEntityInWorld(entity);
		}
		initialized = true;
	}
	
	@Listener(channel=MSG_PERFORM2, side=Side.SERVER)
	public void s_perform2()  {
		if (this.getStatus() == Status.TERMINATED) {
			return;
		}
		kill();
		end();
	}
	
	@Listener(channel=MSG_TICK, side=Side.SERVER)
	public void s_tick()  {
		if (!initialized) {
			return;
		}
		if (this.getStatus() == Status.TERMINATED) {
			return;
		}
		if (entity == null || entity.isDead) {
			end();
			return;
		}
		if(tick()) {
			ctx.addSkillExp(getExpIncr());
		} else {
			kill();
			end();
		}
	}
	
	@Listener(channel=MSG_TICK, side=Side.CLIENT)
	public void l_tick()  {
		if (this.getStatus() == Status.TERMINATED) {
			return;
		}
		createParticle();
		createParticle();
	}
	
	@SideOnly(Side.CLIENT)
	private void createParticle() {
		World worldcl = Minecraft.getMinecraft().theWorld;
		EntityPlayer playercl = Minecraft.getMinecraft().thePlayer;
		double extraX = new Random().nextDouble() * EntityTurbulentStorm.getWidth(ctx.getSkillExp()) / 2;
		double extraY = new Random().nextDouble() * EntityTurbulentStorm.getHeight(ctx.getSkillExp()) / 2;
		double extraZ = new Random().nextDouble() * EntityTurbulentStorm.getWidth(ctx.getSkillExp()) / 2;

		EntityParticleFX entityfx = new EntityStormParticleFX(worldcl, playercl.posX + extraX, playercl.posY + extraY, playercl.posZ + extraZ, player);
		worldcl.spawnEntityInWorld(entityfx);
	}

	private float getExpIncr()  {
		return MathUtils.lerpf(0.002f, 0.001f, ctx.getSkillExp());
	}
	
	private void end() {
		ctx.setCooldown((int)MathUtils.lerpf(120, 80, ctx.getSkillExp()));
		terminate();
	}
	
	private void kill() {
		if (entity != null) {
			if (!entity.isDead) {
				entity.setDead();
			}
		}
	}
		
}