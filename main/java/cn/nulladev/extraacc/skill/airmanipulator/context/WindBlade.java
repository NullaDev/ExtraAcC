package cn.nulladev.extraacc.skill.airmanipulator.context;

import cn.academy.ability.api.context.Context;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.lambdalib.util.generic.MathUtils;
import cn.nulladev.extraacc.entity.EntityWindBlade;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillWindBlade;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class WindBlade extends Context {
	
	static final String MSG_PERFORM = "perform";
	
	private float cp;

	public WindBlade(EntityPlayer _player) {
		super(_player, SkillWindBlade.INSTANCE);
		
		cp = MathUtils.lerpf(250, 400, ctx.getSkillExp());
	}
	
	private boolean consume() {
		float overload = MathUtils.lerpf(30, 18, ctx.getSkillExp());
		return ctx.consume(overload, cp);
	}
	
	@Listener(channel=MSG_KEYDOWN, side=Side.CLIENT)
	public void l_keydown()  {
		sendToServer(MSG_PERFORM);
	}
	
	@Listener(channel=MSG_PERFORM, side=Side.SERVER)
	public void s_perform()  {
		if(consume()) {
			World world = player.worldObj;
			
			EntityWindBlade gun = new EntityWindBlade(world, player, ctx.getSkillExp(), player.getLookVec());
			world.spawnEntityInWorld(gun);
			
			ctx.addSkillExp(getExpIncr());
			ctx.setCooldown((int)MathUtils.lerpf(60, 30, ctx.getSkillExp()));
		}
	    terminate();
	}

	private float getExpIncr()  {
		return MathUtils.lerpf(0.002f, 0.003f, ctx.getSkillExp());
	}
		
}
