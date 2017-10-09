package cn.nulladev.extraacc.skill.airmanipulator.context;

import cn.academy.ability.api.context.Context;
import cn.academy.ability.api.data.AbilityData;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.lambdalib.util.generic.MathUtils;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillAirCooling;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class AirCooling extends Context {
	
static final String MSG_PERFORM = "perform";
	
	private float cp;

	public AirCooling(EntityPlayer _player) {
		super(_player, SkillAirCooling.INSTANCE);
		
		cp = MathUtils.lerpf(600, 1000, ctx.getSkillExp());
	}
	
	private boolean consume() {
		return ctx.consume(0, cp);
	}
	
	@Listener(channel=MSG_KEYDOWN, side=Side.CLIENT)
	public void l_keydown()  {
		sendToServer(MSG_PERFORM);
	}
	
	@Listener(channel=MSG_PERFORM, side=Side.SERVER)
	public void s_perform()  {
		if(consume()) {
			float new_overload;
			if (player.worldObj.provider.isHellWorld) {
				new_overload = Math.min(ctx.cpData.getOverload(), ctx.cpData.getMaxOverload() / 2);
			} else {
				float f = MathUtils.lerpf(50, 200, ctx.getSkillExp());
				new_overload = Math.max(ctx.cpData.getOverload() - f, 0);
				player.extinguish();
			}
			ctx.cpData.setOverload(new_overload);
			ctx.addSkillExp(getExpIncr());
			ctx.setCooldown((int)MathUtils.lerpf(200, 100, ctx.getSkillExp()));
		}
	    terminate();
	}

	private float getExpIncr()  {
		return MathUtils.lerpf(0.003f, 0.002f, ctx.getSkillExp());
	}

}
