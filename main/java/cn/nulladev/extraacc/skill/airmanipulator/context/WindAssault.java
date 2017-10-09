package cn.nulladev.extraacc.skill.airmanipulator.context;

import cn.academy.ability.api.context.Context;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.lambdalib.util.generic.MathUtils;
import cn.nulladev.extraacc.entity.EntityBasicAirGun;
import cn.nulladev.extraacc.skill.airmanipulator.list.SkillWindAssault;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class WindAssault extends Context {
	
static final String MSG_PERFORM = "perform";
static final String MSG_PERFORM2 = "perform2";
	
	private float cp;
	private float spd;

	public WindAssault(EntityPlayer _player) {
		super(_player, SkillWindAssault.INSTANCE);
		
		cp = MathUtils.lerpf(150, 250, ctx.getSkillExp());
		spd = MathUtils.lerpf(2, 3, ctx.getSkillExp());
	}
	
	private boolean consume() {
		float overload = MathUtils.lerpf(18, 12, ctx.getSkillExp());
		return ctx.consume(overload, cp);
	}
	
	@Listener(channel=MSG_KEYDOWN, side=Side.CLIENT)
	public void l_keydown()  {
		sendToServer(MSG_PERFORM);
	}
	
	@Listener(channel=MSG_PERFORM, side=Side.SERVER)
	public void s_perform()  {
		if(consume()) {
			sendToClient(MSG_PERFORM2);
			player.fallDistance = 0;
			ctx.addSkillExp(getExpIncr());
			ctx.setCooldown((int)MathUtils.lerpf(40, 10, ctx.getSkillExp()));
		}
	    terminate();
	}
	
	@Listener(channel=MSG_PERFORM2, side=Side.CLIENT)
	public void s_perform2()  {
		Vec3 vec = player.getLookVec().normalize();
		double vx = spd * vec.xCoord;
		double vy = spd * vec.yCoord;
		double vz = spd * vec.zCoord;
		player.addVelocity(vx, vy, vz);	
	    terminate();
	}

	private float getExpIncr()  {
		return MathUtils.lerpf(0.003f, 0.005f, ctx.getSkillExp());
	}

}
