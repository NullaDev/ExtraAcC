package cn.nulladev.extraacc.skill.airmanipulator.context;

import java.util.Random;

import cn.academy.ability.api.context.Context;
import cn.academy.ability.api.context.Context.Status;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.lambdalib.util.generic.MathUtils;
import cn.nulladev.extraacc.entity.EntitySand;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillSandStorm;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SandStorm extends Context {
	
	static final String MSG_PERFORM = "perform";
	static final String MSG_PERFORM2 = "perform2";
	
	private float cp;

	public SandStorm(EntityPlayer _player) {
		super(_player, SkillSandStorm.INSTANCE);
		
		cp = MathUtils.lerpf(800, 400, ctx.getSkillExp());
	}
	
	private boolean consume() {
		float overload = MathUtils.lerpf(40, 20, ctx.getSkillExp());
		return ctx.consume(overload, cp);
	}
	
	private boolean tick() {
		float cp1 = MathUtils.lerpf(20, 40, ctx.getSkillExp());
		return ctx.consume(2, cp1);
	}
	
	private void tick_shot() {
		World world = player.worldObj;
		int i = 0;
		int max = (int) MathUtils.lerpf(3, 6, ctx.getSkillExp());
		while (i++ < max) {
			double x = player.getLookVec().xCoord + 1D * new Random().nextDouble() - 0.5D;
			double y = player.getLookVec().yCoord + 1D * new Random().nextDouble() - 0.5D;
			double z = player.getLookVec().zCoord + 1D * new Random().nextDouble() - 0.5D;
			Vec3 vec = Vec3.createVectorHelper(x, y, z).normalize();
			EntitySand sand = new EntitySand(world, player, ctx.getSkillExp(), vec);
			world.spawnEntityInWorld(sand);
		}	
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
			tick_shot();		
			ctx.addSkillExp(getExpIncr());
		}
	}
	
	@Listener(channel=MSG_PERFORM2, side=Side.SERVER)
	public void s_perform2()  {
		if (this.getStatus() == Status.TERMINATED) {
			return;
		}
		end();
	}
	
	@Listener(channel=MSG_TICK, side=Side.SERVER)
	public void s_tick()  {
		if (this.getStatus() == Status.TERMINATED) {
			return;
		}
		if(tick()) {
			tick_shot();
			ctx.addSkillExp(getExpIncr());
		} else {
			end();
		}
	}

	private float getExpIncr()  {
		return MathUtils.lerpf(0.0001f, 0.0002f, ctx.getSkillExp());
	}
	
	private void end() {
		ctx.setCooldown((int)MathUtils.lerpf(80, 60, ctx.getSkillExp()));
		terminate();
	}
		
}
